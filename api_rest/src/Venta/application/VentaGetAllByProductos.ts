import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaReporteByProductosDTO } from "./VentaDTO";

export class VentaGetAllByProductos {
    constructor(private repository: VentaRepository) { }

    async run(intervalosFecha: Date): Promise<VentaReporteByProductosDTO[]> {
        return await this.repository.getAllByProductos(intervalosFecha);
    }
}