import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLCajeroRepository } from "./infrastructure/MySQLCajeroRepository";
import { CajeroCreate } from "./application/CajeroCreate";
import { CajeroGetOneById } from "./application/CajeroGetOneById";
import { CajeroGetOneByNombre } from "./application/CajeroGetOneByNombre";
import { CajeroController } from "./interfaces/CajeroController";
import { cajeroRouter } from "./interfaces/CajeroRouter";
import { CajeroLogin } from "./application/CajeroLogin";

export function initCajeroModule(pool: Pool): Router {
    const repo = new MySQLCajeroRepository(pool);

    const useCases = {
        login: new CajeroLogin(repo),
        create: new CajeroCreate(repo),
        getOneById: new CajeroGetOneById(repo),
        getOneByNombre: new CajeroGetOneByNombre(repo),
    };

    const controller = new CajeroController(useCases);
    return cajeroRouter(controller);
}