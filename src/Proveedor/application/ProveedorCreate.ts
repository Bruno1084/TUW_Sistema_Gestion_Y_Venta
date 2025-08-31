import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorFechaCreacion } from "../domain/ProveedorFechaCreacion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";

export class ProveedorCreate {
    constructor(private repository: ProveedorRepository) { }

    async run(
        nombre: string,
        direccion: string,
        telefono: string
    ): Promise<void> {
        const proveedor = new Proveedor(
            new ProveedorId(0),
            new ProveedorNombre(nombre),
            new ProveedorDireccion(direccion),
            new ProveedorTelefono(telefono),
            new ProveedorFechaCreacion(new Date()),
            new ProveedorFechaModificacion(new Date())
        );

        await this.repository.create(proveedor);
    }
}