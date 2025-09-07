import type { RubroEsActivo } from "./RubroEsActivo";
import type { RubroFechaCracion } from "./RubroFechaCreacion";
import type { RubroFechaModificacion } from "./RubroFechaModificacion";
import type { RubroId } from "./RubroId";
import type { RubroNombre } from "./RubroNombre";

export class Rubro {
    id: RubroId;
    nombre: RubroNombre;
    fechaCreación: RubroFechaCracion;
    fechaModificacion: RubroFechaModificacion;
    esActivo: RubroEsActivo;

    constructor(id: RubroId, nombre: RubroNombre, fechaCreacion: RubroFechaCracion, fechaModificacion: RubroFechaModificacion, esActivo: RubroEsActivo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreación = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.esActivo = esActivo;
    }
}