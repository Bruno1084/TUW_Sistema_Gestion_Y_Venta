import { EmpleadoId } from "../domain/EmpleadoId";
import type { EmpleadoRepository } from "../domain/EmpleadoRepository";

export class EmpleadoDelete {
    constructor(private repository: EmpleadoRepository) {}

    async run(id: number): Promise <void> {
        await this.repository.delete(new EmpleadoId(id));
    }
}