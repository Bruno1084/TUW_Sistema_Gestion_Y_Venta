import type { EmpleadoId } from "./EmpleadoId";
import type { EmpleadoNombre } from "./EmpleadoNombre";
import type { EmpleadoDireccion } from "./EmpleadoDireccion";
import type { EmpleadoTelefono } from "./EmpleadoTelefono";
import type { EmpleadoFechaCreacion } from "./EmpleadoFechaCreacion";
import type { EmpleadoFechaModificacion } from "./EmpleadoFechaModificacion";
import type { EmpleadoEsActivo } from "./EmpleadoEsActivo";

export class Empleado {
    id: EmpleadoId;
    nombre: EmpleadoNombre;
    direccion: EmpleadoDireccion;
    telefono: EmpleadoTelefono;
    fechaCreacion: EmpleadoFechaCreacion;
    fechaModificacion: EmpleadoFechaModificacion;
    esActivo: EmpleadoEsActivo;

    constructor(
        id: EmpleadoId,
        nombre: EmpleadoNombre,
        direccion: EmpleadoDireccion,
        telefono: EmpleadoTelefono,
        fechaCreacion: EmpleadoFechaCreacion,
        fechaModificacion: EmpleadoFechaModificacion,
        esActivo: EmpleadoEsActivo
    ) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.esActivo = esActivo;
    }
}