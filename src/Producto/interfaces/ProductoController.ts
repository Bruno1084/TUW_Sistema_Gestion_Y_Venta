import type { Request, Response } from "express";
import type { ProductoRepository } from "../domain/ProductoRepository";
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
                nombre,
                descripcion,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                proveedorId,
                marcaId,
                rubroId
            } = req.body;

            await this.useCases.create.run(
                codigoBarra,
                nombre,
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

            res.status(201).json({ message: "Producto creado correctamente" });
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
            const { codigoBarra } = req.params;
            const producto = await this.useCases.getOneById.run(codigoBarra!);

            if (!producto) {
                res.status(404).json({ error: "Producto no encontrado" });
                return;
            }

            res.status(201).json(producto);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async updateProducto(req: Request, res: Response): Promise<void> {
        try {
            const { codigoBarra } = req.params;
            const {
                nombre,
                descripcion,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                proveedorId,
                marcaId,
                rubroId
            } = req.body;

            await this.useCases.update.run(
                codigoBarra!,
                {
                    nombre,
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

            res.status(200).json({ message: 'Producto actualizado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}