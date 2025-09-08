import type { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";
import type { MarcaRepository } from "../domain/MarcaRepository";

export class MarcaGetOneById {
    constructor(private repository: MarcaRepository) {}

    async run(id: number): Promise<Marca | null> {
        const marca = this.repository.getOneById(new MarcaId(id));

        if(!marca) {
            throw new Error('Marca no encontrada');
        }

        return marca;
    }
}