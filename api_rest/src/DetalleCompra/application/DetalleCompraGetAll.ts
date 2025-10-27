import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";

export class DetalleCompraGetAll {
    constructor(private repository: DetalleCompraRepository) { }

    async run(): Promise<DetalleCompraDTO[]> {
        return await this.repository.getAll();
    }
}