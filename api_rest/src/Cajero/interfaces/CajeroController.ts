import type { Request, Response } from "express";
import type { CajeroCreate } from "../application/CajeroCreate"
import type { CajeroGetOneById } from "../application/CajeroGetOneById";
import type { CajeroGetOneByNombre } from "../application/CajeroGetOneByNombre";

type CajeroUseCases = {
    create: CajeroCreate;
    getOneById: CajeroGetOneById;
    getOneByNombre: CajeroGetOneByNombre;
}

export class CajeroController {
    constructor(private useCases: CajeroUseCases) { }

    async createCajero(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                direccion,
                telefono,
                contrasenia
            } = req.body;

            await this.useCases.create.run(
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date(),
                contrasenia
            );

            res.status(201).json({ message: "Empleado creado correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getOneByIdCajero(req: Request, res: Response): Promise<void> {
        try {
            const { id} = req.params;
            const cajero = await this.useCases.getOneById.run(Number(id));

            if (!cajero) {
                res.status(404).json({ error: "Cajero no encontrado" });
                return;
            }

            res.status(201).json(cajero);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByNombreCajero(req: Request, res: Response): Promise<void> {
        try {
            const { nombre } = req.body;
            const cajero = await this.useCases.getOneByNombre.run(nombre);

            if (!cajero) {
                res.status(404).json({ error: "Cajero no encontrado" });
                return;
            }

            res.status(201).json(cajero);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }
}