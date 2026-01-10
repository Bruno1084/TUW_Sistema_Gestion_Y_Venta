import type { CompraId } from "./CompraId";
import type { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import type { UsuarioId } from "../../Usuario/domain/UsuarioId";
import type { CompraFechaCreacion } from "./CompraFechaCreacion";
import type { CompraPrecioTotal } from "./CompraPrecioTotal";

export class Compra {
    id: CompraId;
    precioTotal: CompraPrecioTotal;
    fechaCreacion: CompraFechaCreacion;
    proveedorId: ProveedorId;
    usuarioId: UsuarioId;

    constructor(
        id: CompraId,
        precioTotal: CompraPrecioTotal,
        fechaCreacion: CompraFechaCreacion,
        proveedorId: ProveedorId,
        usuarioId: UsuarioId
    ) {
        this.id = id;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
        this.proveedorId = proveedorId;
        this.usuarioId = usuarioId;
    }

    toJSON() {
        return {
            id: this.id.value,
            precioTotal: this.precioTotal.value,
            fechaCreacion: this.fechaCreacion.value,
            proveedorId: this.proveedorId.value,
            usuarioId: this.usuarioId.value
        };
    }
}