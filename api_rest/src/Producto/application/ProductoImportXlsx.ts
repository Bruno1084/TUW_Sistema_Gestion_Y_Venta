import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoExcelRowDTO } from "./ProductoDTO";
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
    constructor(private repository: ProductoRepository) { }

    async run(buffer: Buffer): Promise<void> {

        const workbook = read(buffer, { type: 'buffer' });
        const sheetName = workbook.SheetNames[0];
        const worksheet: WorkSheet = workbook.Sheets[sheetName!]!;

        const jsonData = utils.sheet_to_json<ProductoExcelRowDTO>(worksheet, {
            defval: null,
            raw: false
        });

        const proveedores = [...new Set(jsonData.map(r => r["Proveedor"]))];
        const marcas = [...new Set(jsonData.map(r => r["Marca"]))];
        const rubros = [...new Set(jsonData.map(r => r["Rubro"]))];

        // const proveedoresDB = 
        // const marcasDB =
        // const rubrosDB =

        const productos = jsonData.map((row, index) => {
            const normalizedRow = this.normalizeRow(row);

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
                    new ProveedorId(normalizedRow["ID de Proveedor"]),
                    new MarcaId(normalizedRow["ID de Marca"]),
                    new RubroId(normalizedRow["ID de Rubro"])
                );
            } catch (error) {
                throw new Error(`Error en fila ${index + 2}: ${(error as Error).message}`);
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