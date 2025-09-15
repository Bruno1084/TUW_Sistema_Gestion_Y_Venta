import type { Request, Response } from "express";
import { ProveedorCreate } from "../application/ProveedorCreate"
import { ProveedorDelete } from "../application/ProveedorDelete";
import { ProveedorGetAll } from "../application/ProveedorGetAll";
import { ProveedorGetOneById } from "../application/ProveedorGetOneById";
import { ProveedorUpdate } from "../application/ProveedorUpdate";

type ProveedorUseCases = {
    create: ProveedorCreate;
    getAll: ProveedorGetAll;
    getOneById: ProveedorGetOneById;
    update: ProveedorUpdate;
    delete: ProveedorDelete;
}

export class ProveedorController {
    constructor(private useCases: ProveedorUseCases) { }

    async createProveedor(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                direccion,
                telefono,
            } = req.body;

            const proveedorCreado = await this.useCases.create.run(
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date()
            );

            res.status(201).json(proveedorCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllProveedor(req: Request, res: Response): Promise<void> {
        try {
            const proveedores = await this.useCases.getAll.run();
            res.status(200).json(proveedores);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdProveedor(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const proveedor = await this.useCases.getOneById.run(Number(id));

            if (!proveedor) {
                res.status(404).json({ error: "Producto no encontrado" });
                return;
            }

            res.status(200).json(proveedor);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async updateProveedor(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            const {
                nombre,
                direccion,
                telefono,
            } = req.body;

            const proveedorActualizado = await this.useCases.update.run(
                Number(id),
                {
                    nombre,
                    direccion,
                    telefono,
                }
            );

            res.status(200).json(proveedorActualizado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async deleteProveedor(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(204).json({ message: 'Proveedor eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}