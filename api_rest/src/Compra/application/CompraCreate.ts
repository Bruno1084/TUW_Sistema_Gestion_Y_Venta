import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraDetailDTO } from "./CompraDTO";
import type { DetalleCompraDTO } from "../../DetalleCompra/application/DetalleCompraDTO";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { UsuarioId } from "../../Usuario/domain/UsuarioId";
import { Compra } from "../domain/Compra";
import { CompraFechaCreacion } from "../domain/CompraFechaCreacion";
import { CompraId } from "../domain/CompraId";
import { CompraPrecioTotal } from "../domain/CompraPrecioTotal";
import { DetalleCompra } from "../../DetalleCompra/domain/DetalleCompra";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { DetalleCompraCantidad } from "../../DetalleCompra/domain/DetalleCompraCantidad";
import { DetalleCompraPrecioTotal } from "../../DetalleCompra/domain/DetalleCompraPrecioTotal";
import { DetalleCompraPrecioUnitario } from "../../DetalleCompra/domain/DetalleCompraPrecioUnitario";

export class CompraCreate {
    constructor(private repository: CompraRepository) { }

    async run(
        precioTotal: number,
        fechaCreacion: Date,
        proveedorId: number,
        usuarioId: number,
        detalles: DetalleCompraDTO[]
    ): Promise<CompraDetailDTO> {
        const compra = new Compra(
            new CompraId(0),
            new CompraPrecioTotal(precioTotal),
            new CompraFechaCreacion(fechaCreacion),
            new ProveedorId(proveedorId),
            new UsuarioId(usuarioId)
        );

        const detallesCompra = detalles.map(detalle =>
            new DetalleCompra(
                new CompraId(detalle.compraId),
                new ProductoCodigoBarra(detalle.productoCodigoBarra),
                new DetalleCompraCantidad(detalle.cantidad),
                new DetalleCompraPrecioTotal(detalle.precioTotal),
                new DetalleCompraPrecioUnitario(detalle.precioUnitario)
            )
        )

        return await this.repository.create(compra, detallesCompra);
    }
}