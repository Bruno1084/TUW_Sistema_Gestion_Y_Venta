import { CompraId } from "../../Compra/domain/CompraId";
import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";

export class DetalleCompraGetAllFromCompraById {
    constructor(private repository: DetalleCompraRepository) { }

    async run(compraId: number): Promise<DetalleCompraDTO[]> {
        return await this.repository.getAllFromCompraById(new CompraId(compraId));
    }
}