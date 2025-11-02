import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import { VentaId } from "../../Venta/domain/VentaId";
import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaGetOneById {
    constructor(private repository: DetalleVentaRepository) { }

    async run(ventaId: number, productoCodigoBarra: string): Promise<DetalleVentaDTO> {
        const detalleVenta = await this.repository.getOneById(new VentaId(ventaId), new ProductoCodigoBarra(productoCodigoBarra));
        if (!detalleVenta) throw new Error('Detalle Venta no encontrada');

        return detalleVenta;
    }
}