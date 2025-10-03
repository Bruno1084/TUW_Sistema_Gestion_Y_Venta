import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraDetailDTO } from "./CompraDTO";

export class CompraGetAllWithDetail {
    constructor(private repository: CompraRepository) { }

    async run(): Promise<CompraDetailDTO[]> {
        return await this.repository.getAllWithDetail();
    }
}