import type { RubroRepository } from "../domain/RubroRepository"
import type { RubroDTO } from "./RubroDTO";
import { Rubro } from "../domain/Rubro";
import { RubroId } from "../domain/RubroId"
import { RubroNombre } from "../domain/RubroNombre";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";
import { RubroFechaCracion } from "../domain/RubroFechaCreacion";

export class RubroUpdate {
    constructor(private repository: RubroRepository) { }

    async run(
        id: number,
        updates: {
            nombre: string
        }
    ): Promise<RubroDTO> {
        const rubroExistente = await this.repository.getOneById(new RubroId(id));
        if (!rubroExistente) throw new Error("Rubro no encontrado");

        const rubroActualizado = new Rubro(
            new RubroId(id),
            updates.nombre ? new RubroNombre(updates.nombre) : new RubroNombre(rubroExistente.nombre),
            new RubroFechaCracion(rubroExistente.fechaCreacion),
            new RubroFechaModificacion(new Date()),
        );

        return await this.repository.update(rubroActualizado);
    }
}