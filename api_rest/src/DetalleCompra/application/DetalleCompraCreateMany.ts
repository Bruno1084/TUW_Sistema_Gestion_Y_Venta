import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";
import { CompraId } from "../../Compra/domain/CompraId";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { DetalleCompra } from "../domain/DetalleCompra";
import { DetalleCompraCantidad } from "../domain/DetalleCompraCantidad";
import { DetalleCompraPrecioTotal } from "../domain/DetalleCompraPrecioTotal";
import { DetalleCompraPrecioUnitario } from "../domain/DetalleCompraPrecioUnitario";


export class DetalleCompraCreateMany {
    constructor(private repository: DetalleCompraRepository) { }

    async run(detalles: DetalleCompraDTO[]): Promise<DetalleCompraDTO[]> {
        const compraDetalles = detalles.map(d =>
            new DetalleCompra(
                new CompraId(d.compraId),
                new ProductoCodigoBarra(d.productoCodigoBarra),
                new DetalleCompraCantidad(d.cantidad),
                new DetalleCompraPrecioTotal(d.precioTotal),
                new DetalleCompraPrecioUnitario(d.precioUnitario)
            )
        );

        return await this.repository.createMany(compraDetalles);
    }
}