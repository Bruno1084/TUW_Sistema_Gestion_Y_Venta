import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDetailDTO } from "./VentaDTO";
import type { DetalleVentaDTO } from "../../DetalleVenta/application/DetalleVentaDTO";
import { Venta } from "../domain/Venta";
import { VentaId } from "../domain/VentaId";
import { VentaPrecioTotal } from "../domain/VentaPrecioTotal";
import { VentaFechaCreacion } from "../domain/VentaFechaCreacion";
import { ClienteId } from "../../Cliente/domain/ClienteId";
import { DetalleVenta } from "../../DetalleVenta/domain/DetalleVenta";
import { UsuarioId } from "../../Usuario/domain/UsuarioId";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { DetalleVentaCantidad } from "../../DetalleVenta/domain/DetalleVentaCantidad";
import { DetalleVentaPrecioTotal } from "../../DetalleVenta/domain/DetalleVentaPrecioTotal";
import { DetalleVentaPrecioUnitario } from "../../DetalleVenta/domain/DetalleVentaPrecioUnitario";

export class VentaCreate {
    constructor(private repository: VentaRepository) { }

    async run(
        precioTotal: number,
        fechaCreacion: Date,
        clienteId: number,
        usuarioId: number,
        detalles: DetalleVentaDTO[]
    ): Promise<VentaDetailDTO> {
        const venta = new Venta(
            new VentaId(0),
            new ClienteId(clienteId),
            new UsuarioId(usuarioId),
            new VentaPrecioTotal(precioTotal),
            new VentaFechaCreacion(fechaCreacion),
        );

        const detallesVenta = detalles.map(detalle =>
            new DetalleVenta(
                new VentaId(detalle.ventaId),
                new ProductoCodigoBarra(detalle.productoCodigoBarra),
                new DetalleVentaCantidad(detalle.cantidad),
                new DetalleVentaPrecioTotal(detalle.precioTotal),
                new DetalleVentaPrecioUnitario(detalle.precioUnitario)
            )
        );

        return await this.repository.create(venta, detallesVenta);
    }
}