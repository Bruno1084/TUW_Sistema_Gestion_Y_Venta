import type { Proveedor } from "../domain/Proveedor";
import type { ProveedorRepository } from "../domain/ProveedorRepository";

export class ProveedorGetAll {
    constructor(private repository: ProveedorRepository) {}

    async run(): Promise<Proveedor[]> {
        return this.repository.getAll();
    }
}