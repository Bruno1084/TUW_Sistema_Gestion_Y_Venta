import type { Marca } from "../domain/Marca";
import type { MarcaRepository } from "../domain/MarcaRepository";

export class MarcaGetAll {
    constructor(private repository: MarcaRepository) {}

    async run(): Promise<Marca[]> {
        return this.repository.getAll();
    }
}