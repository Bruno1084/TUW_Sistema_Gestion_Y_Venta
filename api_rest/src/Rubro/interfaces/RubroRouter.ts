import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { MySQLRubroRepository } from "../infrastructure/MySQLRubroRepository";
import { RubroCreate } from "../application/RubroCreate";
import { RubroGetAll } from "../application/RubroGetAll";
import { RubroGetOneById } from "../application/RubroGetOneById";
import { RubroUpdate } from "../application/RubroUpdate";
import { RubroDelete } from "../application/RubroDelete";
import { RubroController } from "./RubroController";
import { RubroFindByNames } from "../application/RubroFindByNames";

export function rubroRouter(pool: Pool): Router {
    const repo = new MySQLRubroRepository(pool);
    const useCases = {
        create: new RubroCreate(repo),
        getAll: new RubroGetAll(repo),
        getOneById: new RubroGetOneById(repo),
        update: new RubroUpdate(repo),
        delete: new RubroDelete(repo),
        findByNames: new RubroFindByNames(repo)
    };

    const controller = new RubroController(useCases);
    const router = Router();

    router.post('/rubros', controller.create.bind(controller));
    router.get('/rubros', controller.getAll.bind(controller));
    router.get('/rubros/:id', controller.getOneById.bind(controller));
    router.put('/rubros/:id', controller.update.bind(controller));
    router.delete('/rubros/:id', controller.delete.bind(controller));
    router.post('/rubros/names', controller.findByName.bind(controller));

    return router;
}