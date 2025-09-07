import type { RubroRepository } from "../domain/RubroRepository"
import { Rubro } from "../domain/Rubro";
import { RubroId } from "../domain/RubroId"
import { RubroNombre } from "../domain/RubroNombre";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";

export class RubroUpdate {
    constructor(private repository: RubroRepository) { }

    async run(
        id: number,
        updates: {
            nombre: string
        }
    ): Promise<void> {
        const rubroExistente = await this.repository.getOneById(new RubroId(id));
        if (!rubroExistente) throw new Error("Rubro no encontrado");

        const rubroActualizado = new Rubro(
            new RubroId(id),
            updates.nombre ? new RubroNombre(updates.nombre) : rubroExistente.nombre,
            rubroExistente.fechaCreación,
            new RubroFechaModificacion(new Date()),
            rubroExistente.esActivo
        );

        await this.repository.update(rubroActualizado);
    }
}