import type { ProveedorId } from "./ProveedorId";
import type { ProveedorNombre } from "./ProveedorNombre";
import type { ProveedorDireccion } from "./ProveedorDireccion";
import type { ProveedorTelefono } from "./ProveedorTelefono";
import type { ProveedorFechaCreacion } from "./ProveedorFechaCreacion";
import type { ProveedorFechaModificacion } from "./ProveedorFechaModificacion";
import type { ProveedorEsActivo } from "./ProveedorEsActivo";

export class Proveedor {
    id: ProveedorId;
    nombre: ProveedorNombre;
    direccion: ProveedorDireccion;
    telefono: ProveedorTelefono;
    fechaCreacion: ProveedorFechaCreacion;
    fechaModificacion: ProveedorFechaModificacion;
    esActivo: ProveedorEsActivo;

    constructor(
        id: ProveedorId,
        nombre: ProveedorNombre,
        direccion: ProveedorDireccion,
        telefono: ProveedorTelefono,
        fechaCreacion: ProveedorFechaCreacion,
        fechaModificacion: ProveedorFechaModificacion,
        esActivo: ProveedorEsActivo
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