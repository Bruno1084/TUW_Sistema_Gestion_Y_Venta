import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLCompraRepository } from "./infrastructure/MySQLCompraRepository";
import { CompraCreate } from "./application/CompraCreate";
import { CompraGetAll } from "./application/CompraGetAll";
import { CompraGetOneById } from "./application/CompraGetOneById";
import { CompraController } from "./interfaces/CompraController";
import { compraRouter } from "./interfaces/CompraRouter";

export function initCompraModule(pool: Pool): Router {
    const repo = new MySQLCompraRepository(pool);

    const useCases = {
        create: new CompraCreate(repo),
        getAll: new CompraGetAll(repo),
        getOneById: new CompraGetOneById(repo)
    };

    const controller = new CompraController(useCases);
    return compraRouter(controller);
}