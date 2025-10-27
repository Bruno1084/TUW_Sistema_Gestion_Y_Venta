import { CompraId } from "../../Compra/domain/CompraId";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { DetalleCompra } from "../domain/DetalleCompra";
import { DetalleCompraCantidad } from "../domain/DetalleCompraCantidad";
import { DetalleCompraPrecioTotal } from "../domain/DetalleCompraPrecioTotal";
import { DetalleCompraPrecioUnitario } from "../domain/DetalleCompraPrecioUnitario";
import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";

export class DetalleCompraCreate {
    constructor(private repository: DetalleCompraRepository) { }

    async run(
        compraId: number,
        productoCodigoBarra: string,
        cantidad: number,
        precioTotal: number,
        precioUnitario: number
    ): Promise<DetalleCompraDTO> {
        const detalleCompra = new DetalleCompra(
            new CompraId(compraId),
            new ProductoCodigoBarra(productoCodigoBarra),
            new DetalleCompraCantidad(cantidad),
            new DetalleCompraPrecioTotal(precioTotal),
            new DetalleCompraPrecioUnitario(precioUnitario)
        );

        return await this.repository.create(detalleCompra);
    }
}