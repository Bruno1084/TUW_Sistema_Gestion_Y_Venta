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

    async createMarca(req: Request, res: Response): Promise<void> {
        try {
            const { nombre } = req.body;

            await this.useCases.create.run(nombre);

            res.status(201).json({ message: "Cliente creado correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });

        }
    }

    async getAllMarca(req: Request, res: Response): Promise<void> {
        try {
            const marcas = await this.useCases.getAll.run();

            res.status(200).json(marcas);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdMarca(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const marca = await this.useCases.getOneById.run(Number(id));

            if (!marca) {
                res.status(404).json({ error: "Marca no encontrada" });
                return;
            }

            res.status(201).json(marca);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async updateMarca(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const { nombre } = req.body;

            this.useCases.update.run(Number(id), { nombre });

            res.status(201).json({ message: "Marca actualizada correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async deleteMarca(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(200).json({ message: 'Marca eliminada correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

}