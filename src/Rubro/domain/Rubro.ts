import type { RubroId } from "./RubroId";
import type { RubroNombre } from "./RubroNombre";

export class Rubro {
    id: RubroId;
    nombre: RubroNombre;

    constructor(id: RubroId, nombre: RubroNombre) {
        this.id = id;
        this.nombre = nombre;
    }
}