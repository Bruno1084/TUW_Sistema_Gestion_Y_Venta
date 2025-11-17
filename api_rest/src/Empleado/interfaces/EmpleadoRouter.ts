import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { EmpleadoController } from "./EmpleadoController";
import { MySQLEmpleadoRepository } from "../infrastructure/MySQLEmpleadoRepository";
import { EmpleadoCreate } from "../application/EmpleadoCreate";
import { EmpleadoGetAll } from "../application/EmpleadoGetAll";
import { EmpleadoGetOneById } from "../application/EmpleadoGetOneById";
import { EmpleadoUpdate } from "../application/EmpleadoUpdate";
import { EmpleadoDelete } from "../application/EmpleadoDelete";

export function empleadoRouter(pool: Pool): Router {
    const repo = new MySQLEmpleadoRepository(pool);
    const useCases = {
        create: new EmpleadoCreate(repo),
        getAll: new EmpleadoGetAll(repo),
        getOneById: new EmpleadoGetOneById(repo),
        update: new EmpleadoUpdate(repo),
        delete: new EmpleadoDelete(repo)
    };

    const controller = new EmpleadoController(useCases);
    const router = Router();

    router.post('/empleados', controller.create.bind(controller));
    router.get('/empleados/getAll', controller.getAll.bind(controller));
    router.get('/empleados/:id', controller.getOneById.bind(controller));
    router.put('/empleados/:id', controller.update.bind(controller));
    router.delete('/empleados/:id', controller.delete.bind(controller));

    return router;
};
