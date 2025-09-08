import { MarcaId } from "../domain/MarcaId";
import type { MarcaRepository } from "../domain/MarcaRepository";

export class MarcaDelete {
    constructor(private repository: MarcaRepository) {}

    async run(id: number): Promise<void> {
        this.repository.delete(new MarcaId(id));
    }
}