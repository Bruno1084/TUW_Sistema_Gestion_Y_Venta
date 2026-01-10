import { CompraId } from "../domain/CompraId";
import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraDetailDTO } from "./CompraDTO";

export class CompraGetOneByIdWithDetail {
    constructor(private repository: CompraRepository) { }

    async run(id: number): Promise<CompraDetailDTO | null> {
        const compra = await this.repository.getOneByIdWithDetail(new CompraId(id));
        if (!compra) throw new Error('Compra no encontrada');

        return compra;
    }
}