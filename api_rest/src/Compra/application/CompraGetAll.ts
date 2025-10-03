import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraSimpleDTO } from "./CompraDTO";

export class CompraGetAll {
    constructor(private repository: CompraRepository) { }

    async run(): Promise<CompraSimpleDTO[]> {
        return await this.repository.getAll();
    }
}