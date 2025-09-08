import type { Pool } from "mysql2/promise";
import { MySQLCajeroRepository } from "./infrastructure/MySQLCajeroRepository";
import { CajeroCreate } from "./application/CajeroCreate";
import { CajeroGetOneById } from "./application/CajeroGetOneById";
import { CajeroGetOneByNombre } from "./application/CajeroGetOneByNombre";
import { CajeroController } from "./interfaces/CajeroController";
import { cajeroRouter } from "./interfaces/CajeroRouter";

export function initCajeroModule(pool: Pool) {
    const repo = new MySQLCajeroRepository(pool);

    const useCases = {
        create: new CajeroCreate(repo),
        getOneById: new CajeroGetOneById(repo),
        getOneByNombre: new CajeroGetOneByNombre(repo)
    };

    const controller = new CajeroController(useCases);
    return cajeroRouter(controller);
}