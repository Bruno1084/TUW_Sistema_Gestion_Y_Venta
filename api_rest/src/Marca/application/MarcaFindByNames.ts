import type { MarcaRepository } from "../domain/MarcaRepository";
import type { MarcaSimpleDTO } from "./MarcaDTO";

export class MarcaFindByNames {
    constructor(private repository: MarcaRepository) { }

    async run(nombres: string[]): Promise<MarcaSimpleDTO[]> {
        return await this.repository.findByNames(nombres);
    }
}