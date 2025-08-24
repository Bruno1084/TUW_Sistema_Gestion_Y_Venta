import { Producto } from "../domain/Producto";
import type { ProductoRepository } from "../domain/ProductoRepository";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProductoNombre } from "../domain/ProductoNombre";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProductoStock } from "../domain/ProductoStock";
import type { Proveedor } from "../../Proveedor/domain/Proveedor";
import type { Marca } from "../../Marca/domain/Marca";
import type { Rubro } from "../../Rubro/domain/Rubro";

export class ProductoCreate {
    constructor(private repository: ProductoRepository) { }

    async run(
        codigoBarra: string,
        nombre: string,
        descripcion: string,
        precioCompra: number,
        precioVenta: number,
        stock: number,
        imgUri: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
        proveedor: Proveedor,
        marca: Marca,
        rubro: Rubro
    ): Promise<void> {
        const producto = new Producto(
            new ProductoCodigoBarra(codigoBarra),
            new ProductoNombre(nombre),
            new ProductoDescripcion(descripcion),
            new ProductoPrecioCompra(precioCompra),
            new ProductoPrecioVenta(precioVenta),
            new ProductoStock(stock),
            new ProductoImgUri(imgUri),
            new ProductoFechaCreacion(fechaCreacion),
            new ProductoFechaModificacion(fechaModificacion),
            proveedor,
            marca,
            rubro
        );
        
        await this.repository.create(producto);
    }
}