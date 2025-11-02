import type { DetalleCompra } from "../domain/DetalleCompra";
import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";

export class DetalleCompraCreateMany {
    constructor(private repository: DetalleCompraRepository) { }

    async run(detalles: DetalleCompra[]): Promise<DetalleCompraDTO[]> {
        return await this.repository.createMany(detalles);
    }
}