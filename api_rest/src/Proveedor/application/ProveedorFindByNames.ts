import type { ProveedorRepository } from "../domain/ProveedorRepository";
import type { ProveedorSimpleDTO } from "./ProveedorDTO";

export class ProveedorFindByNames {
    constructor(private repository: ProveedorRepository) { }

    async run(nombres: string[]): Promise<ProveedorSimpleDTO[]> {
        return this.repository.findByNames(nombres);
    }
}