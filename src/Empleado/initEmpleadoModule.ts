import type { Pool } from "mysql2/promise";
import type { Router } from "express";
import { MySQLEmpleadoRepository } from "./infrastructure/MySQLEmpleadoRepository";
import { EmpleadoCreate } from "./application/EmpleadoCreate";
import { EmpleadoGetAll } from "./application/EmpleadoGetAll";
import { EmpleadoGetOneById } from "./application/EmpleadoGetOneById";
import { EmpleadoUpdate } from "./application/EmpleadoUpdate";
import { EmpleadoDelete } from "./application/EmpleadoDelete";
import { EmpleadoController } from "./interfaces/EmpleadoController";
import { empleadoRouter } from "./interfaces/EmpleadoRouter";

export function initEmpleadoModule(pool: Pool): Router {
    const repo = new MySQLEmpleadoRepository(pool);

    const useCases = {
        create: new EmpleadoCreate(repo),
        getAll: new EmpleadoGetAll(repo),
        getOneById: new EmpleadoGetOneById(repo),
        update: new EmpleadoUpdate(repo),
        delete: new EmpleadoDelete(repo)
    };

    const controller = new EmpleadoController(useCases);
    return empleadoRouter(controller);
}