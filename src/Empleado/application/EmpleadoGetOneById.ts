import { type EmpleadoRepository } from "../domain/EmpleadoRepository";
import { Empleado } from "../domain/Empleado";
import { EmpleadoId } from "../domain/EmpleadoId";
import { EmpleadoNotFoundError } from "../domain/EmpleadoNotFoundError";

export class EmpleadoGetOneById {
    constructor(private repository: EmpleadoRepository) {}

    async run(id: number): Promise<Empleado | null> {
        const empleado = await this.repository.getOneById(new EmpleadoId(id));

        if(!empleado) {
            throw new EmpleadoNotFoundError('Emplado not found');
        }

        return empleado;
    }
}