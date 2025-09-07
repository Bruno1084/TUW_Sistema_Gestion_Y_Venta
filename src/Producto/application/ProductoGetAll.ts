import type { ProductoRepository } from "../domain/ProductoRepository";
import type { Producto } from "../domain/Producto";

export class ProductoGetAll {
    constructor(private repository: ProductoRepository) { }

    async run(): Promise<Producto[]> {
        return this.repository.getAll();
    }
}