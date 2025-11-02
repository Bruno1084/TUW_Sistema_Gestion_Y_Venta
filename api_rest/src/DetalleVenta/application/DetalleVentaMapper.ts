import type { DetalleVenta } from "../domain/DetalleVenta";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaMapper {
    static toDTO(detalleVenta: DetalleVenta): DetalleVentaDTO {
        return {
            ventaId: detalleVenta.ventaId.value,
            productoCodigoBarra: detalleVenta.productoCodigoBarra.value,
            cantidad: detalleVenta.cantidad.value,
            precioTotal: detalleVenta.precioTotal.value,
            precioUnitario: detalleVenta.precioUnitario.value
        };
    }
}