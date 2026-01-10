import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProductoDetailDTO } from "./ProductoDTO";

export class ProductoGetAllWithDetail {
    constructor(private repository: ProductoRepository) { }

    async run(): Promise<ProductoDetailDTO[]> {
        return this.repository.getAllWithDetail();
    }
}