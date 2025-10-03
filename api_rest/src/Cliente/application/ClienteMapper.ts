import type { Cliente } from "../domain/Cliente";
import type { ClienteDTO } from "./ClienteDTO";

export class ClienteMapper {
    static toDTO(cliente: Cliente): ClienteDTO {
        return {
            id: cliente.id.value,
            nombre: cliente.nombre.value,
            direccion: cliente.direccion.value,
            telefono: cliente.telefono.value,
            fechaCreacion: cliente.fechaCreacion.value,
            fechaModificacion: cliente.fechaModificacion.value,
            esActivo: cliente.esActivo.value
        }
    }
}