import { VentaId } from "../../Venta/domain/VentaId";
import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVentaDTO } from "./DetalleVentaDTO";

export class DetalleVentaGetAllFromVentaById {
    constructor(private repository: DetalleVentaRepository) { }

    async run(ventaId: number): Promise<DetalleVentaDTO[]> {
        return await this.repository.getAllFromVentaById(new VentaId(ventaId));
    }
}