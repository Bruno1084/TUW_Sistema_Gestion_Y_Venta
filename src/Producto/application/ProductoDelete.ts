import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import type { ProductoRepository } from "../domain/ProductoRepository";

export class ProductoDelete {
    constructor(private repository: ProductoRepository) { }

    async run(codigoBarra: string): Promise<void> {
        await this.repository.delete(new ProductoCodigoBarra(codigoBarra));
    }
}