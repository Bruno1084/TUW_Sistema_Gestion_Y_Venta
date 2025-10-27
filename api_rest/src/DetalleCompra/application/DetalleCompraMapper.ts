import type { DetalleCompra } from "../domain/DetalleCompra";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";

export class DetalleCompraMapper {
    static toDTO(detalleCompra: DetalleCompra): DetalleCompraDTO {
        return {
            compraId: detalleCompra.compraId.value,
            productoCodigoBarra: detalleCompra.productoCodigoBarra.value,
            cantidad: detalleCompra.cantidad.value,
            precioTotal: detalleCompra.precioTotal.value,
            precioUnitario: detalleCompra.precioUnitario.value
        };
    }
}