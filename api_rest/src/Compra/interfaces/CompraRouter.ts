import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { CompraController } from "./CompraController";
import { MySQLCompraRepository } from "../infrastructure/MySQLCompraRepository";
import { CompraCreate } from "../application/CompraCreate";
import { CompraGetAll } from "../application/CompraGetAll";
import { CompraGetOneByIdWithDetail } from "../application/CompraGetOneByIdWithDetail";
import { CompraGetAllByProveedores } from "../application/CompraGetAllByProveedores";

export function compraRouter(pool: Pool): Router {
    const repo = new MySQLCompraRepository(pool);
    const useCases = {
        create: new CompraCreate(repo),
        getAll: new CompraGetAll(repo),
        getAllByProveedores: new CompraGetAllByProveedores(repo),
        getOneByIdWithDetail: new CompraGetOneByIdWithDetail(repo),
    };

    const controller = new CompraController(useCases);
    const router = Router();

    router.post('/compras', controller.create.bind(controller));
    router.get('/compras', controller.getAll.bind(controller));
    router.get('/compras/:id', controller.getOneByIdWithDetail.bind(controller));

    // Reportes endpoints
    router.get('/compras/reportes/proveedores/:intervaloFecha', controller.getAllByProveedores.bind(controller));

    return router;
}