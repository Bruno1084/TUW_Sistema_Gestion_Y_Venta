import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorFechaCreacion } from "../domain/ProveedorFechaCreacion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";
import { ProveedorEsActivo } from "../domain/ProveedorEsActivo";

export class ProveedorCreate {
    constructor(private repository: ProveedorRepository) { }

    async run(
        nombre: string,
        direccion: string,
        telefono: string,
        fechaCreacion: Date,
        fechaModificacion: Date
    ): Promise<void> {
        const proveedor = new Proveedor(
            new ProveedorId(0),
            new ProveedorNombre(nombre),
            new ProveedorDireccion(direccion),
            new ProveedorTelefono(telefono),
            new ProveedorFechaCreacion(fechaCreacion),
            new ProveedorFechaModificacion(fechaModificacion),
            new ProveedorEsActivo(true)
        );

        await this.repository.create(proveedor);
    }
}