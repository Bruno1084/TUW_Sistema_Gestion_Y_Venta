import type { Request, Response } from "express"
import type { MarcaCreate } from "../application/MarcaCreate"
import type { MarcaDelete } from "../application/MarcaDelete"
import type { MarcaGetAll } from "../application/MarcaGetAll"
import type { MarcaGetOneById } from "../application/MarcaGetOneById"
import type { MarcaUpdate } from "../application/MarcaUpdate"

type MarcaUseCases = {
    create: MarcaCreate,
    getAll: MarcaGetAll,
    getOneById: MarcaGetOneById,
    update: MarcaUpdate,
    delete: MarcaDelete
}

export class MarcaController {
    constructor(private useCases: MarcaUseCases) { }

    async create(req: Request, res: Response): Promise<void> {
        try {
            const { nombre } = req.body;

            const marcaCreada = await this.useCases.create.run(nombre);

            res.status(201).json(marcaCreada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });

        }
    }

    async getAll(req: Request, res: Response): Promise<void> {
        try {
            const marcas = await this.useCases.getAll.run();

            res.status(200).json(marcas);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneById(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const marca = await this.useCases.getOneById.run(Number(id));

            if (!marca) {
                res.status(404).json({ error: "Marca no encontrada" });
                return;
            }

            res.status(200).json(marca);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async update(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const { nombre } = req.body;

            const marcaActualizada = await this.useCases.update.run(Number(id), { nombre });

            res.status(200).json(marcaActualizada);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async delete(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(204).json({ message: 'Marca eliminada correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}