import type { DetalleCompra } from "../../DetalleCompra/domain/DetalleCompra";
import type { CompraDetailDTO, CompraDTO, CompraReporteByProveedoresDTO } from "../application/CompraDTO";
import type { Compra } from "./Compra";
import type { CompraId } from "./CompraId";

export interface CompraRepository {
    create(compra: Compra, detalles: DetalleCompra[]): Promise<CompraDetailDTO>;
    getAll(): Promise<CompraDTO[]>;
    getAllByProveedores(intervaloFecha: Date): Promise<CompraReporteByProveedoresDTO[]>;
    getOneByIdWithDetail(compraId: CompraId): Promise<CompraDetailDTO | null>;
}