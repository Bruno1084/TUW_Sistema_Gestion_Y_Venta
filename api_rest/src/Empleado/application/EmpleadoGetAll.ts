import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import type { EmpleadoDTO } from "./EmpleadoDTO";

export class EmpleadoGetAll {
    constructor(private repository: EmpleadoRepository) {}

    async run(): Promise<EmpleadoDTO[]> {
        return this.repository.getAll();
    }
}