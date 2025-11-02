import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLDetalleVentaRepository } from "./infrastructure/MySQLDetalleVentaRepository";
import { DetalleVentaCreate } from "./application/DetalleVentaCreate";
import { DetalleVentaGetAll } from "./application/DetalleVentaGetAll";
import { DetalleVentaGetAllFromVentaById } from "./application/DetalleVentaGetAllFromVentaId";
import { DetalleVentaGetOneById } from "./application/DetalleVentaGetOneById";
import { DetalleVentaController } from "./interfaces/DetalleVentaController";
import { detalleVentaRouter } from "./interfaces/DetalleVentaRouter";
import { DetalleVentaCreateMany } from "./application/DetalleVentaCreateMany";

export function initDetalleVentaModule(pool: Pool): Router {
    const repo = new MySQLDetalleVentaRepository(pool);

    const useCases = {
        create: new DetalleVentaCreate(repo),
        createMany: new DetalleVentaCreateMany(repo),
        getAll: new DetalleVentaGetAll(repo),
        getAllFromVentaById: new DetalleVentaGetAllFromVentaById(repo),
        getOneById: new DetalleVentaGetOneById(repo),
    };

    const controller = new DetalleVentaController(useCases);
    return detalleVentaRouter(controller);

}