import type { VentaId } from "./VentaId";
import type { Empleado } from "../../Empleado/domain/Empleado";
import type { VentaPrecioTotal } from "./VentaPrecioTotal";
import type { VentaFechaCreacion } from "./VentaFechaCreacion";

export class Venta {
    id: VentaId;
    cliente: Cliente;
    empleado: Empleado;
    precioTotal: VentaPrecioTotal;
    fechaCreacion: VentaFechaCreacion;

    constructor(
        id: VentaId,
        cliente: Cliente,
        empleado: Empleado,
        precioTotal: VentaPrecioTotal,
        fechaCreacion: VentaFechaCreacion
    ) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
    }
}