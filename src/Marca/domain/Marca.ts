import type { MarcaId } from "./MarcaId";
import type { MarcaNombre } from "./MarcaNombre";
import type { MarcaFechaCreacion } from "./MarcaFechaCreacion";
import type { MarcaFechaModificacion } from "./MarcaFechaModificacion";

export class Marca {
    id: MarcaId;
    nombre: MarcaNombre;
    fechaCreacion: MarcaFechaCreacion;
    fechaModificacion: MarcaFechaModificacion;

    constructor(id: MarcaId, nombre: MarcaNombre,
        fechaCreacion: MarcaFechaCreacion,
        fechaModificacion: MarcaFechaModificacion
    ) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion,
        this.fechaModificacion = fechaModificacion
    }
}