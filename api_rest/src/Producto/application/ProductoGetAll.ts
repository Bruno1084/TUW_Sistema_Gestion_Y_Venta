import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoSimpleDTO } from "./ProductoDTO";

export class ProductoGetAll {
    constructor(private repository: ProductoRepository) { }

    async run(): Promise<ProductoSimpleDTO[]> {
        return await this.repository.getAll();
    }
}