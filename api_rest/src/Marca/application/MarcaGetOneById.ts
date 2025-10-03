import type { MarcaRepository } from "../domain/MarcaRepository";
import type { MarcaDTO } from "./MarcaDTO";
import { MarcaId } from "../domain/MarcaId";

export class MarcaGetOneById {
    constructor(private repository: MarcaRepository) {}

    async run(id: number): Promise<MarcaDTO | null> {
        const marca = this.repository.getOneById(new MarcaId(id));

        if(!marca) {
            throw new Error('Marca no encontrada');
        }

        return marca;
    }
}