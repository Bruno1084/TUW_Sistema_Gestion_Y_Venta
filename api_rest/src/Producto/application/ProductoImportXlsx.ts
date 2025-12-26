import type { ProductoRepository } from "../domain/ProductoRepository";

export class ProductoImportXlsx {
    constructor(private repository: ProductoRepository) { }

    async run(): Promise<void> {


    }
}