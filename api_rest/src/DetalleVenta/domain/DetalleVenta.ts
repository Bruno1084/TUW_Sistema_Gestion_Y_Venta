import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import type { VentaId } from "../../Venta/domain/VentaId";
import type { DetalleVentaCantidad } from "./DetalleVentaCantidad";
import type { DetalleVentaPrecioTotal } from "./DetalleVentaPrecioTotal";
import type { DetalleVentaPrecioUnitario } from "./DetalleVentaprecioUnitario";

export class DetalleVenta {
    ventaId: VentaId;
    productoCodigoBarra: ProductoCodigoBarra;
    cantidad: DetalleVentaCantidad;
    precioTotal: DetalleVentaPrecioTotal;
    precioUnitario: DetalleVentaPrecioUnitario;

    constructor(
        ventaId: VentaId,
        productoCodigoBarra: ProductoCodigoBarra,
        cantidad: DetalleVentaCantidad,
        precioTotal: DetalleVentaPrecioTotal,
        precioUnitario: DetalleVentaPrecioUnitario
    ) {
        this.ventaId = ventaId;
        this.productoCodigoBarra = productoCodigoBarra;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    toJSON() {
        return {
            ventaId: this.ventaId.value,
            productoCodigoBarra: this.productoCodigoBarra.value,
            cantidad: this.cantidad.value,
            precioTotal: this.precioTotal.value,
            precioUnitario: this.precioUnitario.value
        };
    }
}