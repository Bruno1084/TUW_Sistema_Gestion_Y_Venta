import type { Venta } from "./Venta";
import type { VentaId } from "./VentaId";

export interface VentaRepository {
    create(venta: Venta): Promise<Venta>;
    getAll(): Promise<Venta[]>;
    getOneById(ventaId: VentaId): Promise<Venta | null>;
}