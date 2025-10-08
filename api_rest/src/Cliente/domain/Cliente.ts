import type { ClienteDireccion } from "./ClienteDireccion";
import type { ClienteFechaCreacion } from "./ClienteFechaCreacion";
import type { ClienteFechaModificacion } from "./ClienteFechaModificacion";
import type { ClienteId } from "./ClienteId";
import type { ClienteNombre } from "./ClienteNombre";
import type { ClienteTelefono } from "./ClienteTelefono";

export class Cliente {
    id: ClienteId;
    nombre: ClienteNombre;
    direccion: ClienteDireccion;
    telefono: ClienteTelefono;
    fechaCreacion: ClienteFechaCreacion;
    fechaModificacion: ClienteFechaModificacion;

    constructor(
        id: ClienteId,
        nombre: ClienteNombre,
        direccion: ClienteDireccion,
        telefono: ClienteTelefono,
        fechaCreacion: ClienteFechaCreacion,
        fechaModificacion: ClienteFechaModificacion,
    ) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    toJSON() {
        return {
            id: this.id.value,
            nombre: this.nombre.value,
            direccion: this.direccion.value,
            telefono: this.telefono.value,
            fechaCreacion: this.fechaCreacion.value,
            fechaModificacion: this.fechaModificacion.value,
        };
    }
}