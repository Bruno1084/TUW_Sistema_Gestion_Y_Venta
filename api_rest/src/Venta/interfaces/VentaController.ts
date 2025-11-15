import type { Request, Response } from "express"
import type { VentaCreate } from "../application/VentaCreate"
import type { VentaGetAll } from "../application/VentaGetAll"
import type { VentaGetOneByIdWithDetail } from "../application/VentaGetOneByIdWithDetail";

type VentaUseCases = {
    create: VentaCreate;
    getAll: VentaGetAll;
    getOneByIdWithDetail: VentaGetOneByIdWithDetail;
}

export class VentaController {
    constructor(private useCases: VentaUseCases) { }

    async create(req: Request, res: Response): Promise<void> {
        try {
            const {
                precioTotal,
                proveedorId,
                usuarioId,
                detalles
            } = req.body;

            const ventaCreada = await this.useCases.create.run(
                precioTotal,
                new Date(),
                proveedorId,
                usuarioId,
                detalles
            );

            res.status(201).json(ventaCreada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAll(req: Request, res: Response): Promise<void> {
        try {
            const ventas = await this.useCases.getAll.run();
            res.status(200).json(ventas);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdWithDetail(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const venta = await this.useCases.getOneByIdWithDetail.run(Number(id));

            if (!venta) {
                res.status(404).json({ error: "Venta no encontrada" });
                return;
            }

            res.status(200).json(venta);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}