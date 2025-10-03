import type { MarcaRepository } from "../domain/MarcaRepository";
import type { MarcaDTO } from "./MarcaDTO";
import { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";
import { MarcaNombre } from "../domain/MarcaNombre";
import { MarcaFechaModificacion } from "../domain/MarcaFechaModificacion";
import { MarcaFechaCreacion } from "../domain/MarcaFechaCreacion";
import { MarcaEsActivo } from "../domain/MarcaEsActivo";

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
            new MarcaId(marcaExistente.id),
            updates.nombre? new MarcaNombre(updates.nombre) : new MarcaNombre(marcaExistente.nombre),
            new MarcaFechaCreacion(marcaExistente.fechaCreacion),
            new MarcaFechaModificacion(new Date()),
            new MarcaEsActivo(marcaExistente.esActivo)
        );

        return await this.repository.update(marcaActualizada);
    }
}