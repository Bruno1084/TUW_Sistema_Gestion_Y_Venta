import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { MarcaController } from "./MarcaController";
import { MySQLMarcaRepository } from "../infrastructure/MySQLMarcaRepository";
import { MarcaCreate } from "../application/MarcaCreate";
import { MarcaGetAll } from "../application/MarcaGetAll";
import { MarcaGetOneById } from "../application/MarcaGetOneById";
import { MarcaUpdate } from "../application/MarcaUpdate";
import { MarcaDelete } from "../application/MarcaDelete";

export function marcaRouter(pool: Pool): Router {
    const repo = new MySQLMarcaRepository(pool);
    const useCases = {
        create: new MarcaCreate(repo),
        getAll: new MarcaGetAll(repo),
        getOneById: new MarcaGetOneById(repo),
        update: new MarcaUpdate(repo),
        delete: new MarcaDelete(repo)
    };

    const controller = new MarcaController(useCases);
    const router = Router();

    router.post('/marcas', controller.create.bind(controller));
    router.get('/marcas', controller.getAll.bind(controller));
    router.get('/marcas/:id', controller.getOneById.bind(controller));
    router.put('/marcas/:id', controller.update.bind(controller));
    router.delete('/marcas/:id', controller.delete.bind(controller));

    return router;
}