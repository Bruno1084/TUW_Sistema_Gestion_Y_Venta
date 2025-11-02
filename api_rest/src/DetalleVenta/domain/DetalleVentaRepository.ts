import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import type { VentaId } from "../../Venta/domain/VentaId";
import type { DetalleVentaDTO } from "../application/DetalleVentaDTO";
import type { DetalleVenta } from "./DetalleVenta";

export interface DetalleVentaRepository {
    create(detalleVenta: DetalleVenta): Promise<DetalleVentaDTO>;
    getAll(): Promise<DetalleVentaDTO[]>;
    getAllFromVentaById(ventaId: VentaId): Promise<DetalleVentaDTO[]>;
    getOneById(ventaId: VentaId, productoCodigoBarra: ProductoCodigoBarra): Promise<DetalleVentaDTO | null>;
}