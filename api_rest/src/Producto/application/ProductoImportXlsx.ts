import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoExcelRowDTO } from "./ProductoDTO";
import type { ProveedorFindOrCreate } from "../../Proveedor/application/ProveedorFindOrCreate";
import type { RubroFindOrCreate } from "../../Rubro/application/RubroFindOrCreate";
import type { MarcaFindOrCreate } from "../../Marca/application/MarcaFindOrCreate";
import { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProductoStock } from "../domain/ProductoStock";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { MarcaId } from "../../Marca/domain/MarcaId";
import { RubroId } from "../../Rubro/domain/RubroId";
import { read, utils, type WorkSheet } from "xlsx";

export class ProductoImportXlsx {
    constructor(
        private repository: ProductoRepository,
        private proveedorFindOrCreate: ProveedorFindOrCreate,
        private marcaFindOrCreate: MarcaFindOrCreate,
        private rubroFindOrCreate: RubroFindOrCreate
    ) { }

    async run(buffer: Buffer): Promise<void> {

        const workbook = read(buffer, { type: 'buffer' });
        const sheetName = workbook.SheetNames[0];
        const worksheet: WorkSheet = workbook.Sheets[sheetName!]!;

        const jsonData = utils.sheet_to_json<ProductoExcelRowDTO>(worksheet, {
            defval: null,
            raw: false
        });

        const proveedoresExcel = [...new Set(jsonData.map(r => r["Proveedor"]))];
        const marcasExcel = [...new Set(jsonData.map(r => r["Marca"]))];
        const rubrosExcel = [...new Set(jsonData.map(r => r["Rubro"]))];

        const proveedorMap = await this.proveedorFindOrCreate.run(proveedoresExcel);
        const marcaMap = await this.marcaFindOrCreate.run(marcasExcel);
        const rubroMap = await this.rubroFindOrCreate.run(rubrosExcel);

        const productos = jsonData.map((row, index) => {
            const normalizedRow = this.normalizeRow(row);

            const proveedorId = proveedorMap.get(normalizedRow.Proveedor);
            const marcaId = marcaMap.get(normalizedRow.Marca);
            const rubroId = rubroMap.get(normalizedRow.Rubro);

            if (!proveedorId) {
                throw new Error(`Proveedor no encontrado: ${normalizedRow.Proveedor}`);
            }
            if (!marcaId) {
                throw new Error(`Marca no encontrada: ${normalizedRow.Marca}`);
            }
            if (!rubroId) {
                throw new Error(`Rubro no encontrado: ${normalizedRow.Rubro}`);
            }

            try {

                return new Producto(
                    new ProductoCodigoBarra(normalizedRow["Código de Barras"]),
                    new ProductoDescripcion(normalizedRow["Descripción"]),
                    new ProductoPrecioCompra(ProductoPrecioCompra.parsePrecio(normalizedRow["Precio de Compra"])),
                    new ProductoPrecioVenta(ProductoPrecioVenta.parsePrecio(normalizedRow["Precio de Venta"])),
                    new ProductoStock(normalizedRow["Stock"]),
                    new ProductoImgUri(normalizedRow["Imagen Uri"]),
                    ProductoFechaCreacion.now(),
                    ProductoFechaModificacion.now(),
                    new ProveedorId(proveedorId!),
                    new MarcaId(marcaId!),
                    new RubroId(rubroId!)
                );
            } catch (error) {
                throw new Error(
                    `Fila ${index + 2}: ${(error as Error).message}`
                );
            }
        });

        await this.repository.importXlsx(productos);
    }

    // Helper function
    normalizeRow(row: Record<string, any>): Record<string, any> {
        const normalized: Record<string, any> = {};

        for (const key of Object.keys(row)) {
            normalized[key.trim()] = row[key];
        }

        return normalized;
    }
}