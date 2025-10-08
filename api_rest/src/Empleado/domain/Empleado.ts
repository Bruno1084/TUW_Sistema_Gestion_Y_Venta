import type { EmpleadoId } from "./EmpleadoId";
import type { EmpleadoNombre } from "./EmpleadoNombre";
import type { EmpleadoDireccion } from "./EmpleadoDireccion";
import type { EmpleadoTelefono } from "./EmpleadoTelefono";
import type { EmpleadoFechaCreacion } from "./EmpleadoFechaCreacion";
import type { EmpleadoFechaModificacion } from "./EmpleadoFechaModificacion";

export class Empleado {
    id: EmpleadoId;
    nombre: EmpleadoNombre;
    direccion: EmpleadoDireccion;
    telefono: EmpleadoTelefono;
    fechaCreacion: EmpleadoFechaCreacion;
    fechaModificacion: EmpleadoFechaModificacion;

    constructor(
        id: EmpleadoId,
        nombre: EmpleadoNombre,
        direccion: EmpleadoDireccion,
        telefono: EmpleadoTelefono,
        fechaCreacion: EmpleadoFechaCreacion,
        fechaModificacion: EmpleadoFechaModificacion,
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