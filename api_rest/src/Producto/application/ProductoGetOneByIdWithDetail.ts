import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoDetailDTO } from "./ProductoDTO";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoNotFoundError } from "../domain/ProductoNotFoundError";

export class ProductoGetOneByIdWithDetail {
    constructor(private repository: ProductoRepository) { }

    async run(codigoBarra: string): Promise<ProductoDetailDTO | null> {
        const producto = await this.repository.getOneByIdWithDetail(new ProductoCodigoBarra(codigoBarra));
        if (!producto) throw new ProductoNotFoundError('Producto no encontrado');
        
        return producto;
    }
}