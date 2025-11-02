import type { Request, Response } from "express";
import type { DetalleVentaCreate } from "../application/DetalleVentaCreate";
import type { DetalleVentaGetAll } from "../application/DetalleVentaGetAll";
import type { DetalleVentaGetAllFromVentaById } from "../application/DetalleVentaGetAllFromVentaId";
import type { DetalleVentaGetOneById } from "../application/DetalleVentaGetOneById";
import type { DetalleVentaCreateMany } from "../application/DetalleVentaCreateMany";

type DetalleVentaUseCases = {
    create: DetalleVentaCreate;
    createMany: DetalleVentaCreateMany;
    getAll: DetalleVentaGetAll;
    getAllFromVentaById: DetalleVentaGetAllFromVentaById;
    getOneById: DetalleVentaGetOneById;
}

export class DetalleVentaController {
    constructor(private useCases: DetalleVentaUseCases) { }

    async createDetalleVenta(req: Request, res: Response): Promise<void> {
        try {
            const {
                ventaId,
                productoCodigoBarra,
                cantidad,
                precioTotal,
                precioUnitario
            } = req.body;

            const detalleVenta = await this.useCases.create.run(
                ventaId,
                productoCodigoBarra,
                cantidad,
                precioTotal,
                precioUnitario
            );

            res.status(201).json(detalleVenta);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async createManyDetalleVenta(req: Request, res: Response): Promise<void> {
        try {
            const { detalles } = req.body;

            if (!detalles || detalles.length === 0) {
                res.status(400).json({ error: "Datos inválidos" });
                return;
            }

            const result = await this.useCases.createMany.run(detalles);

            res.status(201).json(result);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllDetalleVenta(req: Request, res: Response): Promise<void> {
        try {
            const detalles = await this.useCases.getAll.run();
            res.status(200).json(detalles);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getAllFromVentaByIdDetalleVenta(req: Request, res: Response): Promise<void> {
        try {
            const { ventaId } = req.params;
            const detalles = await this.useCases.getAllFromVentaById.run(Number(ventaId));

            if (!detalles) {
                res.status(404).json({ error: "Detalles de venta no encontrados" });
                return;
            }

            res.status(200).json(detalles);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getOneByIdDetalleVenta(req: Request, res: Response): Promise<void> {
        try {
            const { ventaId, productoCodigoBarra } = req.params;
            const detalle = await this.useCases.getOneById.run(Number(ventaId), productoCodigoBarra!);

            if (!detalle) {
                res.status(404).json({ error: "Detalle de venta no encontrado" });
                return;
            }

            res.status(200).json(detalle);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

}