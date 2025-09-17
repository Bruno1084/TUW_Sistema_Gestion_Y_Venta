import type { CajeroRepository } from "../domain/CajeroRepository";
import type { Cajero } from "../domain/Cajero";
import { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";

export class CajeroGetOneByNombre {
    constructor(private repository: CajeroRepository) { }

    async run(nombre: string): Promise<Cajero | null> {
        const cajero = await this.repository.getOneByNombre(new EmpleadoNombre(nombre));

        if (!cajero) throw new Error('Cajero no encontrado');

        return cajero;
    }
}