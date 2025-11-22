import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraReporteByProveedoresDTO } from "./CompraDTO";

export class CompraGetAllByProveedores {
    constructor(private repository: CompraRepository) { }

    async run(intervalosFecha: Date): Promise<CompraReporteByProveedoresDTO[]> {
        return await this.repository.getAllByProveedores(intervalosFecha);
    }
}