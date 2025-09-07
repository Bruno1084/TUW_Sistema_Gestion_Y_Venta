import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLRubroRepository } from "./infrastructure/MySQLRubroRepository";
import { RubroCreate } from "./application/RubroCreate";
import { RubroGetAll } from "./application/RubroGetAll";
import { RubroGetOneById } from "./application/RubroGetOneById";
import { RubroUpdate } from "./application/RubroUpdate";
import { RubroDelete } from "./application/RubroDelete";
import { RubroController } from "./interfaces/RubroController";
import { rubroRouter } from "./interfaces/RubroRouter";

export function initRubroModule(pool: Pool): Router {
    const repo = new MySQLRubroRepository(pool);

    const useCases = {
        create: new RubroCreate(repo),
        getAll: new RubroGetAll(repo),
        getOneById: new RubroGetOneById(repo),
        update: new RubroUpdate(repo),
        delete: new RubroDelete(repo)
    };

    const controller = new RubroController(useCases);
    return rubroRouter(controller);
}