import type { RubroRepository } from "../domain/RubroRepository";
import type { RubroDTO } from "./RubroDTO";

export class RubroGetAll {
    constructor(private repository: RubroRepository) { }

    async run(): Promise<RubroDTO[]> {
        return this.repository.getAll();
    }
}