import type { ProductoRepository } from "../domain/ProductoRepository";
import { read, utils, type WorkSheet } from "xlsx";

export class ProductoImportXlsx {
    constructor(private repository: ProductoRepository) { }

    async run(buffer: Buffer): Promise<Record<string, any>> {

        const workbook = read(buffer, { type: 'buffer' });
        const sheetName = workbook.SheetNames[0];
        const worksheet: WorkSheet = workbook.Sheets[sheetName!]!;

        const jsonData = utils.sheet_to_json(worksheet, {
            defval: null,
            raw: false
        });

        console.log(jsonData);
        return jsonData;
    }
}