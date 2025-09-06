import type { Request, Response } from "express";
import { ProveedorCreate } from "../application/ProveedorCreate"
import { ProveedorDelete } from "../application/ProveedorDelete";
import { ProveedorGetAll } from "../application/ProveedorGetAll";
import { ProveedorGetOneById } from "../application/ProveedorGetOneById";
import { ProveedorUpdate } from "../application/ProveedorUpdate";
import type { ProveedorRepository } from "../domain/ProveedorRepository";

export class ProveedorController {
    private proveedorCreate: ProveedorCreate;
    private proveedorGetAll: ProveedorGetAll;
    private proveedorGetOneById: ProveedorGetOneById;
    private proveedorUpdate: ProveedorUpdate;
    private proveedorDelete: ProveedorDelete;

    constructor(private repository: ProveedorRepository) {
        this.proveedorCreate = new ProveedorCreate(repository);
        this.proveedorGetAll = new ProveedorGetAll(repository);
        this.proveedorGetOneById = new ProveedorGetOneById(repository);
        this.proveedorUpdate = new ProveedorUpdate(repository);
        this.proveedorDelete = new ProveedorDelete(repository);
    }

    async createProveedor(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                direccion,
                telefono,
            } = req.body;

            await this.proveedorCreate.run(
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date()
            );

            res.status(201).json({ message: "Proveedor creado correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllProveedor(req: Request, res: Response): Promise<void> {
        try {
            const productos = await this.proveedorGetAll.run();
            res.status(200).json(productos);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdProveedor(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const proveedor = await this.proveedorGetOneById.run(Number(id));

            if (!proveedor) {
                res.status(404).json({ error: "Producto no encontrado" });
                return;
            }

            res.status(201).json(proveedor);
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

            this.proveedorUpdate.run(
                Number(id),
                {
                    nombre,
                    direccion,
                    telefono,
                }
            );

            res.status(200).json({ message: "Proveedor actualizado correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async deleteProveedor(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.proveedorDelete.run(Number(id));

            res.status(200).json({ message: 'Proveedor eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}