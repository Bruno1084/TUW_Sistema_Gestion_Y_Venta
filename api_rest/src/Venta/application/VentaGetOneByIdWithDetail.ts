import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDetailDTO } from "./VentaDTO";
import { VentaId } from "../domain/VentaId";

export class VentaGetOneByIdWithDetail {
    constructor(private repository: VentaRepository) { }

    async run(id: number): Promise<VentaDetailDTO | null> {
        const compra = await this.repository.getOneByIdWithDetail(new VentaId(id));
        if (!compra) throw new Error('Venta no encontrada');

        return compra;
    }
}