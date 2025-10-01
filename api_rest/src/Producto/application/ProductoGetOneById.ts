import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoSimpleDTO } from "./ProductoDTO";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoNotFoundError } from "../domain/ProductoNotFoundError";

export class ProductoGetOneById {
    constructor(private repository: ProductoRepository) { }

    async run(codigoBarra: string): Promise<ProductoSimpleDTO | null> {
        const producto = await this.repository.getOneById(new ProductoCodigoBarra(codigoBarra));
        if (!producto) throw new ProductoNotFoundError('Producto no encontrado');

        return producto;
    }
}