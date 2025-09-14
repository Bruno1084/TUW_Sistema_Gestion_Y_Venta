import type { Compra } from "./Compra";
import { CompraId } from "./CompraId";

export interface CompraRepository {
    create(compra: Compra): Promise<Compra>;
    getAll(): Promise<Compra[]>;
    getOneById(compraId: CompraId): Promise<Compra | null>;
}