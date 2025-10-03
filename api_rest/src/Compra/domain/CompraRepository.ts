import type { CompraDetailDTO, CompraSimpleDTO } from "../application/CompraDTO";
import type { Compra } from "./Compra";
import { CompraId } from "./CompraId";

export interface CompraRepository {
    create(compra: Compra): Promise<CompraSimpleDTO>;
    getAll(): Promise<CompraSimpleDTO[]>;
    getAllWithDetail(): Promise<CompraDetailDTO[]>;
    getOneById(compraId: CompraId): Promise<CompraSimpleDTO | null>;
}