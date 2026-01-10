import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraDTO } from "./CompraDTO";

export class CompraGetAll {
    constructor(private repository: CompraRepository) { }

    async run(): Promise<CompraDTO[]> {
        return await this.repository.getAll();
    }
}