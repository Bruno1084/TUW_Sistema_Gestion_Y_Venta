import type { CompraId } from "../../Compra/domain/CompraId";
import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import type { DetalleCompraCantidad } from "./DetalleCompraCantidad";
import type { DetalleCompraPrecioTotal } from "./DetalleCompraPrecioTotal";
import type { DetalleCompraPrecioUnitario } from "./DetalleCompraPrecioUnitario";

export class DetalleCompra {
    compraId: CompraId;
    productoCodigoBarra: ProductoCodigoBarra;
    cantidad: DetalleCompraCantidad;
    precioTotal: DetalleCompraPrecioTotal;
    precioUnitario: DetalleCompraPrecioUnitario;

    constructor(
        compraId: CompraId,
        productoCodigoBarra: ProductoCodigoBarra,
        cantidad: DetalleCompraCantidad,
        precioTotal: DetalleCompraPrecioTotal,
        precioUnitario: DetalleCompraPrecioUnitario
    ) {
        this.compraId = compraId;
        this.productoCodigoBarra = productoCodigoBarra;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
    }

    toJSON() {
        return {
            compraId: this.compraId.value,
            productoCodigoBarra: this.productoCodigoBarra.value,
            cantidad: this.cantidad.value,
            precioTotal: this.precioTotal.value,
            precioUnitario: this.precioUnitario.value
        };
    }
}