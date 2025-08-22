import type { EmpleadoDireccion } from "./EmpleadoDireccion";
import type { EmpleadoFechaCreacion } from "./EmpleadoFechaCreacion";
import type { EmpleadoFechaModificacion } from "./EmpleadoFechaModificacion";
import { EmpleadoId } from "./EmpleadoId";
import { EmpleadoNombre } from "./EmpleadoNombre";
import type { EmpleadoTelefono } from "./EmpleadoTelefono";

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
        fechaModificacion: EmpleadoFechaModificacion
    ) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }
}