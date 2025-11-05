import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { VentaId } from "../../Venta/domain/VentaId";
import { DetalleVenta } from "../domain/DetalleVenta";
import { DetalleVentaCantidad } from "../domain/DetalleVentaCantidad";
import { DetalleVentaPrecioTotal } from "../domain/DetalleVentaPrecioTotal";
import { DetalleVentaPrecioUnitario } from "../domain/DetalleVentaPrecioUnitario";

export class DetalleVentaCreateMany {
    constructor(private repository: DetalleVentaRepository) { }

    async run(detalles: DetalleVentaDTO[]): Promise<DetalleVentaDTO[]> {
        const ventaDetalles = detalles.map(d =>
            new DetalleVenta(
                new VentaId(d.ventaId),
                new ProductoCodigoBarra(d.productoCodigoBarra),
                new DetalleVentaCantidad(d.cantidad),
                new DetalleVentaPrecioTotal(d.precioTotal),
                new DetalleVentaPrecioUnitario(d.precioUnitario)
            )
        );

        return await this.repository.createMany(ventaDetalles);
    }
}