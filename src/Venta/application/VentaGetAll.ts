import type { VentaRepository } from "../domain/VentaRepository";

export class VentaGetAll {
    constructor(private repository: VentaRepository) {}

    async run() {
        const ventas = await this.repository.getAll();
        return ventas;
    }
}