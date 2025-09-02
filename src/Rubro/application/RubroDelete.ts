import type { RubroRepository } from "../domain/RubroRepository";
import { RubroId } from "../domain/RubroId";

export class RubroDelete {
    constructor(private repository: RubroRepository) { }

    async run(id: number): Promise<void> {
        await this.repository.delete(new RubroId(id));
    }
}