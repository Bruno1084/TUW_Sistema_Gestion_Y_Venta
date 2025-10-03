import type { RubroRepository } from "../domain/RubroRepository";
import type { RubroDTO } from "./RubroDTO";
import { RubroId } from "../domain/RubroId";

export class RubroGetOneById {
    constructor(private repository: RubroRepository) { }

    async run(id: number): Promise<RubroDTO | null> {
        const rubro = this.repository.getOneById(new RubroId(id));
        if (!rubro) throw new Error('Rubro no encontrado');

        return rubro;
    }
}