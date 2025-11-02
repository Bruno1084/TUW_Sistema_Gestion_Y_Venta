import type { DetalleVenta } from "../domain/DetalleVenta";
import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaCreateMany {
    constructor(private repository: DetalleVentaRepository) { }

    async run(detalles: DetalleVenta[]): Promise<DetalleVentaDTO[]> {
        return await this.repository.createMany(detalles);
    }
}