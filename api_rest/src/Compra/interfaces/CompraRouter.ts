import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { CompraController } from "./CompraController";
import { MySQLCompraRepository } from "../infrastructure/MySQLCompraRepository";
import { CompraCreate } from "../application/CompraCreate";
import { CompraGetAll } from "../application/CompraGetAll";
import { CompraGetOneByIdWithDetail } from "../application/CompraGetOneByIdWithDetail";

export function compraRouter(pool: Pool): Router {
    const repo = new MySQLCompraRepository(pool);
    const useCases = {
        create: new CompraCreate(repo),
        getAll: new CompraGetAll(repo),
        getOneByIdWithDetail: new CompraGetOneByIdWithDetail(repo)
    };

    const controller = new CompraController(useCases);
    const router = Router();

    router.post('/compras', controller.create.bind(controller));
    router.get('/compras', controller.getAll.bind(controller));
    router.get('/compras/:id', controller.getOneByIdWithDetail.bind(controller));

    return router;
}