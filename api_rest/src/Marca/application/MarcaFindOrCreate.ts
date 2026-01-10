import type { MarcaRepository } from "../domain/MarcaRepository";
import { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";
import { MarcaNombre } from "../domain/MarcaNombre";
import { MarcaFechaCreacion } from "../domain/MarcaFechaCreacion";
import { MarcaFechaModificacion } from "../domain/MarcaFechaModificacion";

export class MarcaFindOrCreate {
    constructor(private repository: MarcaRepository) { }

    async run(nombres: string[]): Promise<Map<string, number>> {
        const existentes = await this.repository.findByNames(nombres);

        const map = new Map<string, number>();
        existentes.forEach(p => map.set(p.nombre, p.id));

        const faltantes = nombres.filter(n => !map.has(n));
        for (const nombre of faltantes) {
            const marca = new Marca(
                new MarcaId(0),
                new MarcaNombre(nombre),
                new MarcaFechaCreacion(new Date()),
                new MarcaFechaModificacion(new Date())
            );

            try {
                const creado = await this.repository.create(marca);
                map.set(creado.nombre, creado.id);
            } catch (error: any) {
                throw error;
            }

        }

        return map;
    }
}