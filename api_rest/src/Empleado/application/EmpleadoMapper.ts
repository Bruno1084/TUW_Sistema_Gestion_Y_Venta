import type { Empleado } from "../domain/Empleado";
import type { EmpleadoDTO } from "./EmpleadoDTO";

export class EmpleadoMapper {
    static toDTO(empleado: Empleado): EmpleadoDTO {
        return {
            id: empleado.id.value,
            nombre: empleado.nombre.value,
            direccion: empleado.direccion.value,
            telefono: empleado.telefono.value,
            fechaCreacion: empleado.fechaCreacion.value,
            fechaModificacion: empleado.fechaModificacion.value,
        }
    }
}