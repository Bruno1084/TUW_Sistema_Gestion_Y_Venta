import type { DetalleVenta } from "../../DetalleVenta/domain/DetalleVenta";
import type { VentaDetailDTO, VentaDTO } from "../application/VentaDTO";
import type { Venta } from "./Venta";
import type { VentaId } from "./VentaId";

export interface VentaRepository {
    create(venta: Venta, detalles: DetalleVenta[]): Promise<VentaDetailDTO>;
    getAll(): Promise<VentaDTO[]>;
    getOneByIdWithDetail(ventaId: VentaId): Promise<VentaDetailDTO | null>;
}