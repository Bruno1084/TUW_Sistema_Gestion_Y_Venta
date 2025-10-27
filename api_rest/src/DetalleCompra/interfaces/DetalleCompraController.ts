import type { Request, Response } from "express";
import type { DetalleCompraCreate } from "../application/DetalleCompraCreate"
import type { DetalleCompraGetAll } from "../application/DetalleCompraGetAll";
import type { DetalleCompraGetAllFromCompraById } from "../application/DetalleCompraGetAllFromCompraById";
import type { DetalleCompraGetOneById } from "../application/DetalleCompraGetOneById";

type DetalleCompraUseCases = {
    create: DetalleCompraCreate;
    getAll: DetalleCompraGetAll;
    getAllFromCompraById: DetalleCompraGetAllFromCompraById;
    getOneById: DetalleCompraGetOneById;

}

export class DetalleCompraController {
    constructor(private useCases: DetalleCompraUseCases) { }

    async createDetalleCompra(req: Request, res: Response): Promise<void> {
        try {
            const {
                compraId,
                productoCodigoBarra,
                cantidad,
                precioTotal,
                precioUnitario
            } = req.body;

            const detalleCompra = await this.useCases.create.run(
                compraId,
                productoCodigoBarra,
                cantidad,
                precioTotal,
                precioUnitario
            );

            res.status(201).json(detalleCompra);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllDetalleCompra(req: Request, res: Response): Promise<void> {
        try {
            const detalles = await this.useCases.getAll.run();
            res.status(200).json(detalles);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getAllFromCompraByIdDetalleCompra(req: Request, res: Response): Promise<void> {
        try {
            const { compraId } = req.params;
            const detalles = await this.useCases.getAllFromCompraById.run(Number(compraId));

            if(!detalles) {
                res.status(404).json({ error: "Detalles de compra no encontrados"});
                return;
            }

            res.status(200).json(detalles);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getOneByIdDetalleCompra(req: Request, res: Response): Promise<void> {
        try {
            const { compraId, productoCodigoBarra } = req.params;
            const detalle = await this.useCases.getOneById.run(Number(compraId), productoCodigoBarra!);

            if(!detalle) {
                res.status(404).json({ error: "Detalle de compra no encontrado"});
                return;
            }

            res.status(200).json(detalle);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}