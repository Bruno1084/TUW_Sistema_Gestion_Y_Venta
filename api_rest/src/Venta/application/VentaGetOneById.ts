import { VentaId } from "../domain/VentaId";
import type { VentaRepository } from "../domain/VentaRepository";

export class VentaGetOneById {
    constructor(private repository: VentaRepository) {}

    async run(ventaId: number) {
        const venta = await this.repository.getOneById(new VentaId(ventaId));

        if(!venta) {
            throw new Error('Venta no encontrado')
        }

        return venta;
    }
}