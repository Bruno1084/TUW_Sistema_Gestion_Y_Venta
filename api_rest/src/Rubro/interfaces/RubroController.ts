import type { Request, Response } from "express";
import type { RubroCreate } from "../application/RubroCreate"
import type { RubroDelete } from "../application/RubroDelete";
import type { RubroGetAll } from "../application/RubroGetAll";
import type { RubroGetOneById } from "../application/RubroGetOneById";
import type { RubroUpdate } from "../application/RubroUpdate";

type RubroUseCases = {
    create: RubroCreate;
    getAll: RubroGetAll;
    getOneById: RubroGetOneById;
    update: RubroUpdate;
    delete: RubroDelete;
}

export class RubroController {
    constructor(private useCases: RubroUseCases) { }

    async create(req: Request, res: Response): Promise<void> {
        try {
            const { nombre } = req.body;

            const rubroCreado = await this.useCases.create.run(nombre);

            res.status(201).json(rubroCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAll(req: Request, res: Response): Promise<void> {
        try {
            const clientes = await this.useCases.getAll.run();
            res.status(200).json(clientes);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneById(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const rubro = await this.useCases.getOneById.run(Number(id));

            if (!rubro) {
                res.status(404).json({ error: "Rubro no encontrado" });
                return;
            }

            res.status(200).json(rubro);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async update(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const { nombre } = req.body;

            const rubroActualizado = await this.useCases.update.run(Number(id), { nombre });

            res.status(200).json(rubroActualizado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async delete(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(204).json({ message: 'Cliente eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}