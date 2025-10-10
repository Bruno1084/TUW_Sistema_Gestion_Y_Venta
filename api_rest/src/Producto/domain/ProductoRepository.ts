import type { Producto } from "./Producto";
import type { ProductoDetailDTO, ProductoSimpleDTO } from "../application/ProductoDTO";
import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";

export interface ProductoRepository {
    create(producto: Producto): Promise<ProductoDetailDTO>;
    getAll(): Promise<ProductoSimpleDTO[]>;
    getAllWithDetail(): Promise<ProductoDetailDTO[]>;
    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoSimpleDTO | null>;
    getOneByIdWithDetail(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoDetailDTO | null>;
    update(producto: Producto): Promise<ProductoDetailDTO>;
    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void>;
}