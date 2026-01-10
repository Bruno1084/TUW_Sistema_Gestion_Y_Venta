import type { ProveedorRepository } from "../domain/ProveedorRepository";
import type { ProveedorDTO } from "./ProveedorDTO";
import { ProveedorId } from "../domain/ProveedorId";

export class ProveedorGetOneById {
    constructor(private repository: ProveedorRepository) {}

    async run(id: number): Promise<ProveedorDTO | null> {
        const proveedor = this.repository.getOneById(new ProveedorId(id));
        if(!proveedor) throw new Error('Proveedor no encontrado');

        return proveedor;
    }
}