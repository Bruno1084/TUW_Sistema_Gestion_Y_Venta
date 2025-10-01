import type { Producto } from "./Producto";
import type { ProductoDetailDTO, ProductoSimpleDTO } from "../application/ProductoDTO";
import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";

export interface ProductoRepository {
    create(producto: Producto): Promise<Producto>;
    getAll(): Promise<ProductoSimpleDTO[]>;
    getAllWithDetail(): Promise<ProductoDetailDTO[]>;
    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<Producto | null>;
    update(producto: Producto): Promise<Producto>;
    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void>;
}