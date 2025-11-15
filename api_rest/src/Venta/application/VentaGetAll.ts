import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDTO } from "./VentaDTO";

export class VentaGetAll {
    constructor(private repository: VentaRepository) {}

    async run(): Promise<VentaDTO[]> {
        return await this.repository.getAll();
    }
}