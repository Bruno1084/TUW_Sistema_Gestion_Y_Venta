import type { DetalleCompra } from "../../DetalleCompra/domain/DetalleCompra";
import type { Compra } from "./Compra";
import type { CompraId } from "./CompraId";
import type {
    CompraDetailDTO,
    CompraDTO,
    CompraReporteByProductosDTO,
    CompraReporteByProveedoresDTO
} from "../application/CompraDTO";


export interface CompraRepository {
    create(compra: Compra, detalles: DetalleCompra[]): Promise<CompraDetailDTO>;
    getAll(): Promise<CompraDTO[]>;
    getAllByProveedores(intervaloFecha: Date): Promise<CompraReporteByProveedoresDTO[]>;
    getAllByProductos(intervaloFecha: Date): Promise<CompraReporteByProductosDTO[]>;
    getOneByIdWithDetail(compraId: CompraId): Promise<CompraDetailDTO | null>;
}