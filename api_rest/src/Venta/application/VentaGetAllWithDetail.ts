import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDetailDTO } from "./VentaDTO";

export class VentaGetAllWithDetail {
    constructor(private repository: VentaRepository) {}

    async run(): Promise<VentaDetailDTO[]> {
        return await this.repository.getAllWithDetail();
    }
}