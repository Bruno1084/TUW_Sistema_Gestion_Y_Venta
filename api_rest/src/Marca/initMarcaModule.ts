import type { Pool } from "mysql2/promise";
import type { Router } from "express";
import { MySQLMarcaRepository } from "./infrastructure/MySQLMarcaRepository";
import { marcaRouter } from "./interfaces/MarcaRouter";
import { MarcaController } from "./interfaces/MarcaController";
import { MarcaCreate } from "./application/MarcaCreate";
import { MarcaGetAll } from "./application/MarcaGetAll";
import { MarcaGetOneById } from "./application/MarcaGetOneById";
import { MarcaUpdate } from "./application/MarcaUpdate";
import { MarcaDelete } from "./application/MarcaDelete";

export function initMarcaModule(pool: Pool): Router {
    const repo = new MySQLMarcaRepository(pool);

    const useCases = {
        create: new MarcaCreate(repo),
        getAll: new MarcaGetAll(repo),
        getOneById: new MarcaGetOneById(repo),
        update: new MarcaUpdate(repo),
        delete: new MarcaDelete(repo)
    };

    const controller = new MarcaController(useCases);
    return marcaRouter(controller);
}