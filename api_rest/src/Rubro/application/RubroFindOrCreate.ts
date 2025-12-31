import type { RubroRepository } from "../domain/RubroRepository";
import { Rubro } from "../domain/Rubro";
import { RubroId } from "../domain/RubroId";
import { RubroNombre } from "../domain/RubroNombre";
import { RubroFechaCracion } from "../domain/RubroFechaCreacion";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";

export class RubroFindOrCreate {
    constructor(private repository: RubroRepository) { }

    async run(nombres: string[]): Promise<Map<string, number>> {
        const existentes = await this.repository.findByNames(nombres);

        const map = new Map<string, number>();
        existentes.forEach(p => map.set(p.nombre, p.id));

        const faltantes = nombres.filter(n => !map.has(n));
        for (const nombre of faltantes) {
            const rubro = new Rubro(
                new RubroId(0),
                new RubroNombre(nombre),
                new RubroFechaCracion(new Date()),
                new RubroFechaModificacion(new Date())
            );

            try {
                const creado = await this.repository.create(rubro);
                map.set(creado.nombre, creado.id);
            } catch (error: any) {
                throw error;
            }

        }

        return map;
    }
}