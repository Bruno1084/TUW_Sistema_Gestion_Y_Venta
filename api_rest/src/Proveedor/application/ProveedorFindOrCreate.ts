import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorFechaCreacion } from "../domain/ProveedorFechaCreacion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";

export class ProveedorFindOrCreate {
    constructor(private repository: ProveedorRepository) { }

    async run(nombres: string[]): Promise<Map<string, number>> {
        const existentes = await this.repository.findByNames(nombres);

        const map = new Map<string, number>();
        existentes.forEach(p => map.set(p.nombre, p.id));

        const faltantes = nombres.filter(n => !map.has(n));
        for (const nombre of faltantes) {
            const proveedor = new Proveedor(
                new ProveedorId(0),
                new ProveedorNombre(nombre),
                new ProveedorDireccion(""),
                new ProveedorTelefono(""),
                new ProveedorFechaCreacion(new Date()),
                new ProveedorFechaModificacion(new Date())
            );

            try {
                const creado = await this.repository.create(proveedor);
                map.set(creado.nombre, creado.id);
            } catch (error: any) {
                throw error;
            }

        }

        return map;
    }
}