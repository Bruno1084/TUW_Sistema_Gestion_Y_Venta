import type { RubroRepository } from "../domain/RubroRepository";
import type { RubroSimpleDTO } from "./RubroDTO";

export class RubroFindByNames {
    constructor(private repository: RubroRepository) { } 

    async run(nombres: string[]): Promise<RubroSimpleDTO[]> {
        return this.repository.findByNames(nombres);
    }
}