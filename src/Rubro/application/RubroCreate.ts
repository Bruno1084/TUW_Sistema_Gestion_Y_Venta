import type { RubroRepository } from "../domain/RubroRepository";
import { Rubro } from "../domain/Rubro";
import { RubroFechaCracion } from "../domain/RubroFechaCreacion";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";
import { RubroId } from "../domain/RubroId";
import { RubroNombre } from "../domain/RubroNombre";
import { RubroEsActivo } from "../domain/RubroEsActivo";

export class RubroCreate {
    constructor(private repository: RubroRepository) {}

    async run(
        nombre: string
    ): Promise<void> {
        const rubro = new Rubro(
            new RubroId(0),
            new RubroNombre(nombre),
            new RubroFechaCracion(new Date()),
            new RubroFechaModificacion(new Date()),
            new RubroEsActivo(true)
        );

        await this.repository.create(rubro);
    }
}