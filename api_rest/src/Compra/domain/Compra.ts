import type { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import type { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import type { CompraFechaCreacion } from "./CompraFechaCreacion";
import type { CompraId } from "./CompraId";
import type { CompraPrecioTotal } from "./CompraPrecioTotal";

export class Compra {
    id: CompraId;
    precioTotal: CompraPrecioTotal;
    fechaCreacion: CompraFechaCreacion;
    proveedorId: ProveedorId;
    empleadoId: EmpleadoId;

    constructor(
        id: CompraId,
        precioTotal: CompraPrecioTotal,
        fechaCreacion: CompraFechaCreacion,
        proveedorId: ProveedorId,
        empleadoId: EmpleadoId
    ) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.proveedorId = proveedorId;
        this.empleadoId = empleadoId;
    }
}