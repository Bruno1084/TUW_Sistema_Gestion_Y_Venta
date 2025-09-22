import type { Request, Response } from "express";
import { ProductoCreate } from "../application/ProductoCreate";
import { ProductoGetAll } from "../application/ProductoGetAll";
import { ProductoGetOneById } from "../application/ProductoGetOneById";
import { ProductoUpdate } from "../application/ProductoUpdate";

type ProductoUseCases = {
    create: ProductoCreate;
    getAll: ProductoGetAll;
    getOneById: ProductoGetOneById;
    update: ProductoUpdate;
}

export class ProductoController {
    constructor(private useCases: ProductoUseCases) { }

    async createProducto(req: Request, res: Response): Promise<void> {
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

    async getAllProducto(req: Request, res: Response): Promise<void> {
        try {
            const productos = await this.useCases.getAll.run();
            res.status(200).json(productos);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdProducto(req: Request, res: Response): Promise<void> {
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

    async updateProducto(req: Request, res: Response): Promise<void> {
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
}