import type { Request, Response } from "express";
import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import { EmpleadoCreate } from "../application/EmpleadoCreate";
import { EmpleadoGetAll } from "../application/EmpleadoGetAll";

export class EmpleadoController {
    private empleadoCreate: EmpleadoCreate;
    private empleadoGetAll: EmpleadoGetAll;

    constructor(private repository: EmpleadoRepository) {
        this.empleadoCreate = new EmpleadoCreate(this.repository);
        this.empleadoGetAll = new EmpleadoGetAll(this.repository);
    }

    async createEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const { id, nombre, direccion, telefono } = req.body;

            await this.empleadoCreate.run(
                id,
                nombre,
                direccion,
                telefono,
                new Date(),
                new Date(),
                true
            );

            res.status(201).json({ message: "Empleado creado correctamente" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getAllEmpleado(req: Request, res: Response): Promise<void> {
        try {
            const empleados = await this.empleadoGetAll.run();
            res.json(empleados);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }
}
