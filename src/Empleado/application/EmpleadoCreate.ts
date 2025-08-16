import { Empleado } from "../domain/Empleado";
import { EmpleadoId } from "../domain/EmpleadoId";
import { EmpleadoNombre } from "../domain/EmpleadoNombre";
import { EmpleadoDireccion } from "../domain/EmpleadoDireccion";
import { EmpleadoTelefono } from "../domain/EmpleadoTelefono";
import { EmpleadoFechaCreacion } from "../domain/EmpleadoFechaCreacion";
import { EmpleadoFechaModificacion } from "../domain/EmpleadoFechaModificacion";
import { type EmpleadoRepository } from "../domain/EmpleadoRepository";

export class EmpleadoCreate {
    constructor(private repository: EmpleadoRepository) {}

    async run(
        id: number,
        nombre: string,
        direccion: string,
        telefono: string,
        fechaCreacion: Date,
        fechaModificacion: Date
    ): Promise<void> {
        const empleado = new Empleado(
            new EmpleadoId(id),
            new EmpleadoNombre(nombre),
            new EmpleadoDireccion(direccion),
            new EmpleadoTelefono(telefono),
            new EmpleadoFechaCreacion(fechaCreacion),
            new EmpleadoFechaModificacion(fechaModificacion));
        return this.repository.create(empleado);
    }
}