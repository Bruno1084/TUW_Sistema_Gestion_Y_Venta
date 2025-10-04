import { VentaId } from "../domain/VentaId";
import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaSimpleDTO } from "./VentaDTO";

export class VentaGetOneById {
    constructor(private repository: VentaRepository) {}

    async run(ventaId: number): Promise<VentaSimpleDTO> {
        const venta = await this.repository.getOneById(new VentaId(ventaId));

        if(!venta) {
            throw new Error('Venta no encontrado')
        }

        return venta;
    }
}