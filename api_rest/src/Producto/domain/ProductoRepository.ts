import type { Producto } from "./Producto";
import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";

export interface ProductoRepository {
    create(producto: Producto): Promise<void>
    getAll(): Promise<Producto[]>
    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<Producto | null>
    update(producto: Producto): Promise<void>
    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void>
}