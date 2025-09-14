import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLVentaRepository } from "./infrastructure/MySQLVentaRepository";
import { VentaCreate } from "./application/VentaCreate";
import { VentaGetAll } from "./application/VentaGetAll";
import { VentaGetOneById } from "./application/VentaGetOneById";
import { ventaRouter } from "./interfaces/VentaRouter";
import { VentaController } from "./interfaces/VentaController";

export function initVentaModule(pool: Pool): Router {
    const repo = new MySQLVentaRepository(pool);

    const useCases = {
        create: new VentaCreate(repo),
        getAll: new VentaGetAll(repo),
        getOneById: new VentaGetOneById(repo),
    };

    const controller = new VentaController(useCases);
    return ventaRouter(controller);
}