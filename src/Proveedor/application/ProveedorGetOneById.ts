import type { Proveedor } from "../domain/Proveedor";
import { ProveedorId } from "../domain/ProveedorId";
import type { ProveedorRepository } from "../domain/ProveedorRepository";

export class ProveedorGetOneById {
    constructor(private repository: ProveedorRepository) {}

    async run(id: number): Promise<Proveedor | null> {
        const proveedor = this.repository.getOneById(new ProveedorId(id));
        if(!proveedor) throw new Error('Proveedor no encontrado');

        return proveedor;
    }
}