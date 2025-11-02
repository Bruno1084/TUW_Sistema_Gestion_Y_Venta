import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaGetAll {
    constructor(private repository: DetalleVentaRepository) { }

    async run(): Promise<DetalleVentaDTO[]> {
        return await this.repository.getAll();
    }
}