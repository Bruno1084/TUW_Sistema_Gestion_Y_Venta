import type { ProveedorRepository } from "../domain/ProveedorRepository";
import type { ProveedorDTO } from "./ProveedorDTO";

export class ProveedorGetAll {
    constructor(private repository: ProveedorRepository) {}

    async run(): Promise<ProveedorDTO[]> {
        return this.repository.getAll();
    }
}