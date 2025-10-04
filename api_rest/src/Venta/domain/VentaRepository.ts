import type { VentaDetailDTO, VentaSimpleDTO } from "../application/VentaDTO";
import type { Venta } from "./Venta";
import type { VentaId } from "./VentaId";

export interface VentaRepository {
    create(venta: Venta): Promise<VentaSimpleDTO>;
    getAll(): Promise<VentaSimpleDTO[]>;
    getAllWithDetail(): Promise<VentaDetailDTO[]>;
    getOneById(ventaId: VentaId): Promise<VentaSimpleDTO | null>;
}