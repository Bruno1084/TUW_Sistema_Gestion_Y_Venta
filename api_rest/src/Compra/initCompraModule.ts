import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLCompraRepository } from "./infrastructure/MySQLCompraRepository";
import { CompraCreate } from "./application/CompraCreate";
import { CompraGetAll } from "./application/CompraGetAll";
import { CompraGetAllWithDetail } from "./application/CompraGetAllWithDetail";
import { CompraGetOneById } from "./application/CompraGetOneById";
import { CompraGetOneByIdWithDetail } from "./application/CompraGetOneByIdWithDetail";
import { CompraController } from "./interfaces/CompraController";
import { compraRouter } from "./interfaces/CompraRouter";

export function initCompraModule(pool: Pool): Router {
    const repo = new MySQLCompraRepository(pool);

    const useCases = {
        create: new CompraCreate(repo),
        getAll: new CompraGetAll(repo),
        getAllWithDetail: new CompraGetAllWithDetail(repo),
        getOneById: new CompraGetOneById(repo),
        getOneByIdWithDetail: new CompraGetOneByIdWithDetail(repo)
    };

    const controller = new CompraController(useCases);
    return compraRouter(controller);
}