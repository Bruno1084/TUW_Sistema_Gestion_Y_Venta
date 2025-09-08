import type { RubroRepository } from "../domain/RubroRepository";
import type { Rubro } from "../domain/Rubro";

export class RubroGetAll {
    constructor(private repository: RubroRepository) { }

    async run(): Promise<Rubro[]> {
        return this.repository.getAll();
    }
}