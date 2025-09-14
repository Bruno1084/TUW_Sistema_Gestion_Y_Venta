import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import { Empleado } from "../domain/Empleado";
import { EmpleadoDireccion } from "../domain/EmpleadoDireccion";
import { EmpleadoFechaModificacion } from "../domain/EmpleadoFechaModificacion";
import { EmpleadoId } from "../domain/EmpleadoId";
import { EmpleadoNombre } from "../domain/EmpleadoNombre";
import { EmpleadoTelefono } from "../domain/EmpleadoTelefono";

export class EmpleadoUpdate {
    constructor(private repository: EmpleadoRepository) { }

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string
        }
    ): Promise<Empleado> {
        const empleadoExistente = await this.repository.getOneById(new EmpleadoId(id));
        if (!empleadoExistente) throw new Error("Empleado no encontrado");


        const empleadoActualizado = new Empleado(
            empleadoExistente.id,
            updates.nombre ? new EmpleadoNombre(updates.nombre) : empleadoExistente.nombre,
            updates.direccion ? new EmpleadoDireccion(updates.direccion) : empleadoExistente.direccion,
            updates.telefono ? new EmpleadoTelefono(updates.telefono) : empleadoExistente.telefono,
            empleadoExistente.fechaCreacion,
            new EmpleadoFechaModificacion(new Date()),
            empleadoExistente.esActivo
        );

        return await this.repository.update(empleadoActualizado);
    }
}