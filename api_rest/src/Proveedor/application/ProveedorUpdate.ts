import type { ProveedorRepository } from "../domain/ProveedorRepository";
import type { ProveedorDTO } from "./ProveedorDTO";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";
import { ProveedorFechaCreacion } from "../domain/ProveedorFechaCreacion";

export class ProveedorUpdate {
    constructor(private repository: ProveedorRepository) { }

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string,
        }
    ): Promise<ProveedorDTO> {
        const proveedorExistente = await this.repository.getOneById(new ProveedorId(id));
        if (!proveedorExistente) throw new Error("Proveedor no encontrado");

        const proveedorActualizado = new Proveedor(
            new ProveedorId(proveedorExistente.id),
            updates.nombre ? new ProveedorNombre(updates.nombre) : new ProveedorNombre(proveedorExistente.nombre),
            new ProveedorDireccion(updates.direccion !== undefined ? updates.direccion : proveedorExistente.direccion),
            new ProveedorTelefono(updates.telefono !== undefined ? updates.telefono : proveedorExistente.telefono),
            new ProveedorFechaCreacion(proveedorExistente.fechaCreacion),
            new ProveedorFechaModificacion(new Date()),
        );

        return await this.repository.update(proveedorActualizado);
    }
}