import type { MarcaRepository } from "../domain/MarcaRepository";
import { Marca } from "../domain/Marca";
import { MarcaFechaCreacion } from "../domain/MarcaFechaCreacion";
import { MarcaFechaModificacion } from "../domain/MarcaFechaModificacion";
import { MarcaId } from "../domain/MarcaId";
import { MarcaNombre } from "../domain/MarcaNombre";
import { MarcaEsActivo } from "../domain/MarcaEsActivo";

export class MarcaCreate {
    constructor(private repository: MarcaRepository) {}

    async run(
        nombre: string
    ): Promise<void> {
        const marca = new Marca(
            new MarcaId(0),
            new MarcaNombre(nombre),
            new MarcaFechaCreacion(new Date()),
            new MarcaFechaModificacion(new Date()),
            new MarcaEsActivo(true)
        );

        await this.repository.create(marca);
    }
}