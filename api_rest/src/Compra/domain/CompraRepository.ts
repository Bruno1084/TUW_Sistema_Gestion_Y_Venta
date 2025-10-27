import type { CompraDetailDTO, CompraSimpleDTO } from "../application/CompraDTO";
import type { Compra } from "./Compra";
import type { CompraId } from "./CompraId";

export interface CompraRepository {
    create(compra: Compra): Promise<CompraDetailDTO>;
    getAll(): Promise<CompraSimpleDTO[]>;
    getAllWithDetail(): Promise<CompraDetailDTO[]>;
    getOneById(compraId: CompraId): Promise<CompraSimpleDTO | null>;
    getOneByIdWithDetail(compraId: CompraId): Promise<CompraDetailDTO | null>;
}