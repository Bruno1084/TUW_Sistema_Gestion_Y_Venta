import type { VentaId } from "./VentaId";
import type { VentaPrecioTotal } from "./VentaPrecioTotal";
import type { VentaFechaCreacion } from "./VentaFechaCreacion";
import type { ClienteId } from "../../Cliente/domain/ClienteId";
import type { UsuarioId } from "../../Usuario/domain/UsuarioId";

export class Venta {
    id: VentaId;
    clienteId: ClienteId;
    usuarioId: UsuarioId;
    precioTotal: VentaPrecioTotal;
    fechaCreacion: VentaFechaCreacion;

    constructor(
        id: VentaId,
        clienteId: ClienteId,
        usuarioId: UsuarioId,
        precioTotal: VentaPrecioTotal,
        fechaCreacion: VentaFechaCreacion
    ) {
        this.id = id;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.precioTotal = precioTotal;
        this.fechaCreacion = fechaCreacion;
    }

    toJSON() {
        return {
            id: this.id.value,
            clienteId: this.clienteId.value,
            usuarioId: this.usuarioId.value,
            precioTotal: this.precioTotal.value,
            fechaCreacion: this.fechaCreacion.value
        };
    }
}