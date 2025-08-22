import type { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import type { ProductoRepository } from "../domain/ProductoRepository";
import { ProductoNotFoundError } from "../domain/ProductoNotFoundError";

export class ProductoGetOneById {
    constructor(private repository: ProductoRepository) {}

    async run(codigoBarra: string): Promise<Producto | null> {
        const producto = await this.repository.getOneById(new ProductoCodigoBarra(codigoBarra));

        if(!producto) {
            throw new ProductoNotFoundError('Producto Not Found');
        }

        return producto;
    }
}