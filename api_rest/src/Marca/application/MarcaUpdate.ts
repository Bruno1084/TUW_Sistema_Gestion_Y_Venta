import type { MarcaRepository } from "../domain/MarcaRepository";
import type { MarcaDTO } from "./MarcaDTO";
import { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";
import { MarcaNombre } from "../domain/MarcaNombre";
import { MarcaFechaModificacion } from "../domain/MarcaFechaModificacion";

export class MarcaUpdate {
    constructor(private repository: MarcaRepository) {}

    async run(
        id: number,
        updates: {
            nombre?: string
        }
    ): Promise<MarcaDTO> {
        const marcaExistente = await this.repository.getOneById(new MarcaId(id));
        if (!marcaExistente) throw new Error("Marca no encontrada");

        const marcaActualizada = new Marca(
            marcaExistente.id,
            updates.nombre? new MarcaNombre(updates.nombre) : marcaExistente.nombre,
            marcaExistente.fechaCreacion,
            new MarcaFechaModificacion(new Date()),
            marcaExistente.esActivo
        );

        return await this.repository.update(marcaActualizada);
    }
}