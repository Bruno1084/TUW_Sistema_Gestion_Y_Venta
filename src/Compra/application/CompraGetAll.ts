import type { CompraRepository } from "../domain/CompraRepository";
import type { Compra } from "../domain/Compra";

export class CompraGetAll {
    constructor(private repository: CompraRepository) { }

    async run(): Promise<Compra[]> {
        return this.repository.getAll();
    }
}