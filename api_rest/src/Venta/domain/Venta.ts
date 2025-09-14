import type { VentaId } from "./VentaId";
import type { VentaPrecioTotal } from "./VentaPrecioTotal";
import type { VentaFechaCreacion } from "./VentaFechaCreacion";
import type { ClienteId } from "../../Cliente/domain/ClienteId";
import type { EmpleadoId } from "../../Empleado/domain/EmpleadoId";

export class Venta {
    id: VentaId;
    clienteId: ClienteId;
    empleadoId: EmpleadoId;
    precioTotal: VentaPrecioTotal;
    fechaCreacion: VentaFechaCreacion;

    constructor(
        id: VentaId,
        clienteId: ClienteId,
        empleadoId: EmpleadoId,
        precioTotal: VentaPrecioTotal,
        fechaCreacion: VentaFechaCreacion
    ) {
        this.id = id;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
    }
}