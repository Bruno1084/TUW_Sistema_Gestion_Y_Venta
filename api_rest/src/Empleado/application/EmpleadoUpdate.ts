import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import type { EmpleadoDTO } from "./EmpleadoDTO";
import { Empleado } from "../domain/Empleado";
import { EmpleadoDireccion } from "../domain/EmpleadoDireccion";
import { EmpleadoFechaModificacion } from "../domain/EmpleadoFechaModificacion";
import { EmpleadoId } from "../domain/EmpleadoId";
import { EmpleadoNombre } from "../domain/EmpleadoNombre";
import { EmpleadoTelefono } from "../domain/EmpleadoTelefono";
import { EmpleadoFechaCreacion } from "../domain/EmpleadoFechaCreacion";

export class EmpleadoUpdate {
    constructor(private repository: EmpleadoRepository) { }

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string
        }
    ): Promise<EmpleadoDTO> {
        const empleadoExistente = await this.repository.getOneById(new EmpleadoId(id));
        if (!empleadoExistente) throw new Error("Empleado no encontrado");


        const empleadoActualizado = new Empleado(
            new EmpleadoId(empleadoExistente.id),
            updates.nombre ? new EmpleadoNombre(updates.nombre) : new EmpleadoNombre(empleadoExistente.nombre),
            updates.direccion ? new EmpleadoDireccion(updates.direccion) : new EmpleadoDireccion(empleadoExistente.direccion),
            updates.telefono ? new EmpleadoTelefono(updates.telefono) : new EmpleadoTelefono(empleadoExistente.telefono),
            new EmpleadoFechaCreacion(empleadoExistente.fechaCreacion),
            new EmpleadoFechaModificacion(new Date()),
        );

        return await this.repository.update(empleadoActualizado);
    }
}