import { Empleado } from "../domain/Empleado";
import { type EmpleadoRepository } from "../domain/EmpleadoRepository";

export class EmpleadoGetAll {
    constructor(private repository: EmpleadoRepository) {}

    async run(): Promise<Empleado[]> {
        return this.repository.getAll();
    }
}