import type { CompraId } from "../../Compra/domain/CompraId";
import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";
import type { DetalleCompraDTO } from "../application/DetalleCompraDTO";
import type { DetalleCompra } from "./DetalleCompra";

export interface DetalleCompraRepository {
    create(detalleCompra: DetalleCompra): Promise<DetalleCompraDTO>;
    getAll(): Promise<DetalleCompraDTO[]>;
    getAllFromCompraById(compraId: CompraId): Promise<DetalleCompraDTO[]>;
    getOneById(compraId: CompraId, productoCodigoBarra: ProductoCodigoBarra): Promise<DetalleCompraDTO | null>;
}