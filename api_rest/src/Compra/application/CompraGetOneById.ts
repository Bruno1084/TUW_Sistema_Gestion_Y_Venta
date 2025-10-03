import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraSimpleDTO } from "./CompraDTO";
import { CompraId } from "../domain/CompraId";

export class CompraGetOneById {
    constructor(private repository: CompraRepository) { }

    async run(id: number): Promise<CompraSimpleDTO | null> {
        const compra = await this.repository.getOneById(new CompraId(id));
        if (!compra) throw new Error('Compra no encontrada');

        return compra;
    }
}