import { Proveedor } from "../domain/Proveedor";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";

export class ProveedorUpdate {
    constructor(private repository: ProveedorRepository) {}

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string
        }
    ): Promise<void> {
        const proveedorExistente = await this.repository.getOneById(new ProveedorId(id));
        if (!proveedorExistente) throw new Error("Proveedor no encontrado");

        const proveedorActualizado = new Proveedor(
            proveedorExistente.id,
            updates.nombre ? new ProveedorNombre(updates.nombre) : proveedorExistente.nombre,
            updates.direccion ? new ProveedorDireccion(updates.direccion) : proveedorExistente.direccion,
            updates.telefono ? new ProveedorTelefono(updates.telefono) : proveedorExistente.telefono,
            proveedorExistente.fechaCreacion,
            new ProveedorFechaModificacion(new Date())
        );

        await this.repository.update(proveedorActualizado);
    }
}