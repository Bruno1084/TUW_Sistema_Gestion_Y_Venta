import type { MarcaRepository } from "../domain/MarcaRepository";
import type { MarcaDTO } from "./MarcaDTO";

export class MarcaGetAll {
    constructor(private repository: MarcaRepository) {}

    async run(): Promise<MarcaDTO[]> {
        return this.repository.getAll();
    }
}