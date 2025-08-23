import type { Venta } from "./Venta";
import type { VentaId } from "./VentaId";

export interface VentaRepository {
    create(venta: Venta): Promise<void>;
    getAll(): Promise<Venta[]>;
    getOneById(ventaId: VentaId): Promise<Venta | null>;
}