import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaReporteByClientesDTO } from "./VentaDTO";

export class VentaGetAllByClientes {
    constructor(private repository: VentaRepository) { }

    async run(intervalosFecha: Date): Promise<VentaReporteByClientesDTO[]> {
        return await this.repository.getAllByClientes(intervalosFecha);
    }
}