import type { Request, Response } from "express";
import { ProductoGetAllWithDetail } from "../application/ProductoGetAllWithDetail";
import { ProductoCreate } from "../application/ProductoCreate";
import { ProductoGetAll } from "../application/ProductoGetAll";
import { ProductoGetOneById } from "../application/ProductoGetOneById";
import { ProductoGetOneByIdWithDetail } from "../application/ProductoGetOneByIdWithDetail";
import { ProductoUpdate } from "../application/ProductoUpdate";
import { ProductoDelete } from "../application/ProductoDelete";
import { ProductoImportXlsx } from "../application/ProductoImportXlsx";
import { read, utils, type WorkSheet } from "xlsx";

type ProductoUseCases = {
    create: ProductoCreate;
    getAll: ProductoGetAll;
    getAllWithDetail: ProductoGetAllWithDetail;
    getOneById: ProductoGetOneById;
    getOneByIdWithDetail: ProductoGetOneByIdWithDetail;
    update: ProductoUpdate;
    delete: ProductoDelete;
    importXlsx: ProductoImportXlsx;
}

export class ProductoController {
    constructor(private useCases: ProductoUseCases) { }

    async create(req: Request, res: Response): Promise<void> {
        try {
            const {
                codigoBarra,
                descripcion,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                proveedorId,
                marcaId,
                rubroId
            } = req.body;

            const productoCreado = await this.useCases.create.run(
                codigoBarra,
                descripcion,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                new Date(),
                new Date(),
                proveedorId,
                marcaId,
                rubroId
            );

            res.status(201).json(productoCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAll(req: Request, res: Response): Promise<void> {
        try {
            const productos = await this.useCases.getAll.run();
            res.status(200).json(productos);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getAllProductoWithDetail(req: Request, res: Response): Promise<void> {
        try {
            const productos = await this.useCases.getAllWithDetail.run();
            res.status(200).json(productos);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneById(req: Request, res: Response): Promise<void> {
        try {
            const { codigo } = req.params;
            const producto = await this.useCases.getOneById.run(codigo!);

            if (!producto) {
                res.status(404).json({ error: "Producto no encontrado" });
                return;
            }

            res.status(200).json(producto);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getOneByIdWithDetail(req: Request, res: Response): Promise<void> {
        try {
            const { codigo } = req.params;
            const producto = await this.useCases.getOneByIdWithDetail.run(codigo!);

            if (!producto) {
                res.status(404).json({ error: "Producto no encontrado" });
                return;
            }

            res.status(200).json(producto);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async update(req: Request, res: Response): Promise<void> {
        try {
            const { codigo } = req.params;
            const {
                descripcion,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                proveedorId,
                marcaId,
                rubroId
            } = req.body;

            const productoActualizado = await this.useCases.update.run(
                codigo!,
                {
                    descripcion,
                    precioCompra,
                    precioVenta,
                    stock,
                    imgUri,
                    proveedorId,
                    marcaId,
                    rubroId,
                }
            );

            res.status(200).json(productoActualizado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async delete(req: Request, res: Response): Promise<void> {
        try {
            const { codigo } = req.params;

            await this.useCases.delete.run(codigo!);

            res.status(204).json({ message: 'Producto eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async importXlsx(req: Request, res: Response): Promise<void> {
        try {
            const file = req.file;

            if(!file || file.mimetype !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
                res.status(400).send('Please upload an XLSX file.');
            }

            try {
                const workbook = read(file?.buffer, { type: 'buffer' });
                const sheetName = workbook.SheetNames[0];
                const worksheet: WorkSheet = workbook.Sheets[sheetName!]!;

                const jsonData = utils.sheet_to_json(worksheet);

                res.status(201).json({
                    message: "Archivo excel recibido correctamente",
                    data: jsonData
                });
                console.log(jsonData);

            } catch (err: any) {
                res.status(500).send('Error al procesar el archivo excel');
            }
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}