import type { Request, Response } from "express";
import type { ProductoRepository } from "../domain/ProductoRepository";
import { ProductoCreate } from "../application/ProductoCreate";
import { ProductoGetAll } from "../application/ProductoGetAll";

export class ProductoController {
    private productoCreate: ProductoCreate;
    private productoGetAll: ProductoGetAll;

    constructor(private repository: ProductoRepository) {
        this.productoCreate = new ProductoCreate(this.repository);
        this.productoGetAll = new ProductoGetAll(this.repository);
    }

    async createProducto(req: Request, res: Response): Promise<void> {
        try {
            const { codigoBarra,
                idProveedor,
                nombre,
                descripcion,
                idMarca,
                idRubro,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                proveedorId,
                marcaId,
                rubroId
            } = req.body;

            await this.productoCreate.run(
                codigoBarra,
                idProveedor,
                nombre,
                descripcion,
                idMarca,
                idRubro,
                precioCompra,
                precioVenta,
                stock,
                imgUri,
                new Date(),
                new Date(),
                new Proveedor(proveedorId),
                new Marca(marcaId),
                new Rubro(rubroId)
            );

            res.status(201).json({ message: "Producto creado correctamente" });
        } catch(err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllProducto(req: Request, res: Response): Promise<void> {
        try {
            const productos = await this.productoGetAll.run();
            res.json(productos);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }
}