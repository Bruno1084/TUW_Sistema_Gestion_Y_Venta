import type { Request, Response } from "express";
import { ClienteCreate } from "../application/ClienteCreate";
import { ClienteDelete } from "../application/ClienteDelete";
import { ClienteGetAll } from "../application/ClienteGetAll";
import { ClienteGetOneById } from "../application/ClienteGetOneById";
import { ClienteUpdate } from "../application/ClienteUpdate";

type ClienteUseCases = {
    create: ClienteCreate;
    getAll: ClienteGetAll;
    getOneById: ClienteGetOneById;
    update: ClienteUpdate;
    delete: ClienteDelete;
}

export class ClienteController {
    constructor(private useCases: ClienteUseCases) { }

    async createCliente(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                direccion,
                telefono
            } = req.body;

            const clienteCreado = await this.useCases.create.run(
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date(),
            );

            res.status(201).json(clienteCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllCliente(req: Request, res: Response): Promise<void> {
        try {
            const clientes = await this.useCases.getAll.run();
            res.status(200).json(clientes);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdCliente(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const cliente = await this.useCases.getOneById.run(Number(id));

            if (!cliente) {
                res.status(404).json({ error: "Cliente no encontrado" });
                return;
            }

            res.status(200).json(cliente);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async updateCliente(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const {
                nombre,
                direccion,
                telefono
            } = req.body;

            const clienteActualizado = await this.useCases.update.run(Number(id), {
                nombre,
                direccion,
                telefono
            });

            res.status(200).json(clienteActualizado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async deleteCliente(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(200).json({ message: 'Cliente eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}