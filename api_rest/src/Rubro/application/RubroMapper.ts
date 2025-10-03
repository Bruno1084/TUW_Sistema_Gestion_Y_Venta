import type { Rubro } from "../domain/Rubro";
import type { RubroDTO } from "./RubroDTO";

export class RubroMapper {
    static toDTO(rubro: Rubro): RubroDTO {
        return {
            id: rubro.id.value,
            nombre: rubro.nombre.value,
            fechaCreacion: rubro.fechaCreación.value,
            fechaModificacion: rubro.fechaModificacion.value,
            esActivo: rubro.esActivo.value
        }
    }
}