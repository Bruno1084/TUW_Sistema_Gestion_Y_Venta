import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLDetalleCompraRepository } from "../infrastructure/MySQLDetalleCompraRepository";
import { DetalleCompraCreate } from "../application/DetalleCompraCreate";
import { DetalleCompraGetAll } from "../application/DetalleCompraGetAll";
import { DetalleCompraGetAllFromCompraById } from "../application/DetalleCompraGetAllFromCompraById";
import { DetalleCompraGetOneById } from "../application/DetalleCompraGetOneById";
import { DetalleCompraController } from "./DetalleCompraController";
import { detalleCompraRouter } from "./DetalleCompraRouter";

export function initDetalleCompraModule(pool: Pool): Router {
    const repo = new MySQLDetalleCompraRepository(pool);

    const useCases = {
        create: new DetalleCompraCreate(repo),
        getAll: new DetalleCompraGetAll(repo),
        getAllFromCompraById: new DetalleCompraGetAllFromCompraById(repo),
        getOneById: new DetalleCompraGetOneById(repo),
    };

    const controller = new DetalleCompraController(useCases);
    return detalleCompraRouter(controller);

}