import type { Producto } from "../domain/Producto";
import type { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import type { ProductoRepository } from "../domain/ProductoRepository";

export class MySQLProductoRepository implements ProductoRepository {
    constructor() {}

    create(producto: Producto): Promise<void> {
        
    }

    getAll(): Promise<Producto[]> {
        
    }

    getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<Producto | null> {
        
    }

    update(producto: Producto): Promise<void> {
        
    }

    delete(productocodigoBarra: ProductoCodigoBarra): Promise<void> {
        
    }
}