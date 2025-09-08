import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import type { MarcaId } from "../../Marca/domain/MarcaId";
import type { RubroId } from "../../Rubro/domain/RubroId";
import { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProductoStock } from "../domain/ProductoStock";
import { ProductoEsActivo } from "../domain/ProductoEsActivo";

export class ProductoCreate {
    constructor(private repository: ProductoRepository) { }

    async run(
        codigoBarra: string,
        descripcion: string,
        precioCompra: number,
        precioVenta: number,
        stock: number,
        imgUri: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
        proveedorId: ProveedorId,
        marcaId: MarcaId,
        rubroId: RubroId,
    ): Promise<void> {
        const producto = new Producto(
            new ProductoCodigoBarra(codigoBarra),
            new ProductoDescripcion(descripcion),
            new ProductoPrecioCompra(precioCompra),
            new ProductoPrecioVenta(precioVenta),
            new ProductoStock(stock),
            new ProductoImgUri(imgUri),
            new ProductoFechaCreacion(fechaCreacion),
            new ProductoFechaModificacion(fechaModificacion),
            proveedorId,
            marcaId,
            rubroId,
            new ProductoEsActivo(true)
        );

        await this.repository.create(producto);
    }
}