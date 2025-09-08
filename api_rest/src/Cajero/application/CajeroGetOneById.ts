import type { CajeroRepository } from "../domain/CajeroRepository";
import type { Cajero } from "../domain/Cajero";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";

export class CajeroGetOneById {
    constructor(private repository: CajeroRepository) { }

    async run(id: number): Promise<Cajero | null> {
        const cajero = await this.repository.getOneById(new EmpleadoId(id));

        if (!cajero) {
            throw new Error('Cajero not found');
        }

        return cajero;
    }
}