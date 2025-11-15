import type { Request, Response } from "express";
import type { CompraCreate } from "../application/CompraCreate"
import type { CompraGetAll } from "../application/CompraGetAll";
import type { CompraGetOneByIdWithDetail } from "../application/CompraGetOneByIdWithDetail";

type CompraUseCases = {
    create: CompraCreate;
    getAll: CompraGetAll;
    getOneByIdWithDetail: CompraGetOneByIdWithDetail;
}

export class CompraController {
    constructor(private useCases: CompraUseCases) { }

    async create(req: Request, res: Response): Promise<void> {
        try {
            const {
                precioTotal,
                proveedorId,
                usuarioId,
                detalles
            } = req.body;

            const compraCreada = await this.useCases.create.run(
                precioTotal,
                new Date(),
                proveedorId,
                usuarioId,
                detalles
            );

            res.status(201).json(compraCreada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAll(req: Request, res: Response): Promise<void> {
        try {
            const compras = await this.useCases.getAll.run();
            res.status(200).json(compras);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdWithDetail(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const compra = await this.useCases.getOneByIdWithDetail.run(Number(id));

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