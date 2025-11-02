import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { VentaId } from "../../Venta/domain/VentaId";
import { DetalleVenta } from "../domain/DetalleVenta";
import { DetalleVentaCantidad } from "../domain/DetalleVentaCantidad";
import { DetalleVentaPrecioTotal } from "../domain/DetalleVentaPrecioTotal";
import { DetalleVentaPrecioUnitario } from "../domain/DetalleVentaprecioUnitario";
import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaCreate {
    constructor(private repository: DetalleVentaRepository) { }

    async run(
        ventaId: number,
        productoCodigoBarra: string,
        cantidad: number,
        precioTotal: number,
        precioUnitario: number
    ): Promise<DetalleVentaDTO> {
        const detalleVenta = new DetalleVenta(
            new VentaId(ventaId),
            new ProductoCodigoBarra(productoCodigoBarra),
            new DetalleVentaCantidad(cantidad),
            new DetalleVentaPrecioTotal(precioTotal),
            new DetalleVentaPrecioUnitario(precioUnitario)
        );

        return await this.repository.create(detalleVenta);
    }
}