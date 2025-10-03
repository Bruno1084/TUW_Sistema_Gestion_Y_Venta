import type { Request, Response } from "express";
import type { CompraCreate } from "../application/CompraCreate"
import type { CompraGetAll } from "../application/CompraGetAll";
import type { CompraGetOneById } from "../application/CompraGetOneById";
import type { CompraGetAllWithDetail } from "../application/CompraGetAllWithDetail";

type CompraUseCases = {
    create: CompraCreate;
    getAll: CompraGetAll;
    getAllWithDetail: CompraGetAllWithDetail;
    getOneById: CompraGetOneById;
}

export class CompraController {
    constructor(private useCases: CompraUseCases) { }

    async createCompra(req: Request, res: Response): Promise<void> {
        try {
            const {
                precioTotal,
                proveedorId,
                empleadoId
            } = req.body;

            const compraCreada = await this.useCases.create.run(
                precioTotal,
                new Date(),
                proveedorId,
                empleadoId
            );

            res.status(201).json(compraCreada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllCompra(req: Request, res: Response): Promise<void> {
        try {
            const compras = await this.useCases.getAll.run();
            res.status(200).json(compras);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getAllWithDetail(req: Request, res: Response): Promise<void> {
        try {
            const compras = await this.useCases.getAllWithDetail.run();
            res.status(200).json(compras);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdCompra(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const compra = await this.useCases.getOneById.run(Number(id));

            if (!compra) {
                res.status(404).json({ error: "Compra no encontrada" });
                return;
            }

            res.status(200).json(compra);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}