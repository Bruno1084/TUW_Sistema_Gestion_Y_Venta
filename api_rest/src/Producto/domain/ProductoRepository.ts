import type { Producto } from "./Producto";
import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";

export interface ProductoRepository {
    create(producto: Producto): Promise<Producto>
    getAll(): Promise<Producto[]>
    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<Producto | null>
    update(producto: Producto): Promise<Producto>
    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void>
}