import type { MarcaId } from "./MarcaId";
import type { MarcaNombre } from "./MarcaNombre";
import type { MarcaFechaCreacion } from "./MarcaFechaCreacion";
import type { MarcaFechaModificacion } from "./MarcaFechaModificacion";
import type { MarcaEsActivo } from "./MarcaEsActivo";

export class Marca {
    id: MarcaId;
    nombre: MarcaNombre;
    fechaCreacion: MarcaFechaCreacion;
    fechaModificacion: MarcaFechaModificacion;
    esActivo: MarcaEsActivo;

    constructor(
        id: MarcaId,
        nombre: MarcaNombre,
        fechaCreacion: MarcaFechaCreacion,
        fechaModificacion: MarcaFechaModificacion,
        esActivo: MarcaEsActivo
    ) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.esActivo = esActivo;
    }
}