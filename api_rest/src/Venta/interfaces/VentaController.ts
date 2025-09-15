import type { Request, Response } from "express"
import type { VentaCreate } from "../application/VentaCreate"
import type { VentaGetAll } from "../application/VentaGetAll"
import type { VentaGetOneById } from "../application/VentaGetOneById"

type VentaUseCases = {
    create: VentaCreate,
    getAll: VentaGetAll,
    getOneById: VentaGetOneById
}

export class VentaController {
    constructor(private useCases: VentaUseCases) { }

    async createVenta(req: Request, res: Response): Promise<void> {
        try {
            const {
                clienteId,
                empleadoId,
                precioTotal,
                fechaCreacion
            } = req.body;

            const ventaCreada =  await this.useCases.create.run(
                clienteId,
                empleadoId,
                precioTotal,
                fechaCreacion
            );

            res.status(201).json(ventaCreada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllVenta(req: Request, res: Response): Promise<void> {
        try {
            const ventas = await this.useCases.getAll.run();
            res.status(200).json(ventas);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdVenta(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const venta = await this.useCases.getOneById.run(Number(id));

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
