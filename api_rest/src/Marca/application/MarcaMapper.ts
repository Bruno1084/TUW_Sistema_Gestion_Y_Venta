import type { Marca } from "../domain/Marca";
import type { MarcaDTO } from "./MarcaDTO";

export class MarcaMapper {
    static toDTO(marca: Marca): MarcaDTO {
        return {
            id: marca.id.value,
            nombre: marca.nombre.value,
            fechaCreacion: marca.fechaCreacion.value,
            fechaModificacion: marca.fechaModificacion.value,
            esActivo: marca.esActivo.value
        }
    }
}