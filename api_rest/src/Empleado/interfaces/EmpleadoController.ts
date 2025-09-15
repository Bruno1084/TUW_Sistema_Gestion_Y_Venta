import type { Request, Response } from "express";
import type { EmpleadoCreate } from "../application/EmpleadoCreate";
import type { EmpleadoGetAll } from "../application/EmpleadoGetAll";
import type { EmpleadoGetOneById } from "../application/EmpleadoGetOneById";
import type { EmpleadoUpdate } from "../application/EmpleadoUpdate";
import type { EmpleadoDelete } from "../application/EmpleadoDelete";

type EmpleadoUseCases = {
    create: EmpleadoCreate,
    getAll: EmpleadoGetAll,
    getOneById: EmpleadoGetOneById,
    update: EmpleadoUpdate,
    delete: EmpleadoDelete
}

export class EmpleadoController {
    constructor(private useCases: EmpleadoUseCases) { }

    async createEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                direccion,
                telefono
            } = req.body;

            const empleadoCreado = await this.useCases.create.run(
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date(),
            );

            res.status(201).json(empleadoCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const empleados = await this.useCases.getAll.run();
            res.status(200).json(empleados);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const empleado = await this.useCases.getOneById.run(Number(id));

            if (!empleado) {
                res.status(404).json({ error: "Empleado no encontrado" });
                return;
            }

            res.status(200).json(empleado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async updateEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const {
                nombre,
                direccion,
                telefono,
            } = req.body;

            const empleadoActualizado = await this.useCases.update.run(Number(id), {
                nombre,
                direccion,
                telefono
            });

            res.status(200).json(empleadoActualizado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async deleteEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            await this.useCases.delete.run(Number(id));

            res.status(200).json({ message: 'Empleado eliminado correctamente' });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }
}
