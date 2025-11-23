import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraReporteByProductosDTO } from "./CompraDTO";

export class CompraGetAllByProductos {
    constructor(private repository: CompraRepository) { }

    async run(intervalosFecha: Date): Promise<CompraReporteByProductosDTO[]> {
        return await this.repository.getAllByProductos(intervalosFecha);
    }
}