import type { RubroRepository } from "../domain/RubroRepository";
import { Rubro } from "../domain/Rubro";
import { RubroFechaCracion } from "../domain/RubroFechaCreacion";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";
import { RubroId } from "../domain/RubroId";
import { RubroNombre } from "../domain/RubroNombre";

export class RubroCreate {
    constructor(private repository: RubroRepository) {}

    async run(
        id: number,
        nombre: string
    ): Promise<void> {
        const rubro = new Rubro(
            new RubroId(id),
            new RubroNombre(nombre),
            new RubroFechaCracion(new Date()),
            new RubroFechaModificacion(new Date())
        );

        await this.repository.create(rubro);
    }
}