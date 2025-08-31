import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { ProveedorId } from "../domain/ProveedorId";

export class ProveedorDelete {
    constructor(private repository: ProveedorRepository) { }

    async run(id: number) {
        this.repository.delete(new ProveedorId(id));
    }
}