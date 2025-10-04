import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaSimpleDTO } from "./VentaDTO";

export class VentaGetAll {
    constructor(private repository: VentaRepository) {}

    async run(): Promise<VentaSimpleDTO[]> {
        return await this.repository.getAll();
    }
}