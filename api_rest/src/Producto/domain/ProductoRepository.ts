import type { Producto } from "./Producto";
import type { ProductoDetailDTO, ProductoSimpleDTO } from "../application/ProductoDTO";
import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";

export interface ProductoRepository {
    create(producto: Producto): Promise<ProductoSimpleDTO>;
    getAll(): Promise<ProductoSimpleDTO[]>;
    getAllWithDetail(): Promise<ProductoDetailDTO[]>;
    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoSimpleDTO | null>;
    update(producto: Producto): Promise<ProductoSimpleDTO>;
    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void>;
}