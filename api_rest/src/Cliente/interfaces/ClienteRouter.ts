import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { ClienteController } from "./ClienteController";
import { MySQLClienteRepository } from "../infrastructure/MySQLClienteRepository";
import { ClienteCreate } from "../application/ClienteCreate";
import { ClienteGetAll } from "../application/ClienteGetAll";
import { ClienteGetOneById } from "../application/ClienteGetOneById";
import { ClienteUpdate } from "../application/ClienteUpdate";
import { ClienteDelete } from "../application/ClienteDelete";

export function clienteRouter(pool: Pool): Router {
    const repo = new MySQLClienteRepository(pool);
    const useCases = {
        create: new ClienteCreate(repo),
        getAll: new ClienteGetAll(repo),
        getOneById: new ClienteGetOneById(repo),
        update: new ClienteUpdate(repo),
        delete: new ClienteDelete(repo)
    };

    const controller = new ClienteController(useCases);
    const router = Router();

    router.post('/clientes', controller.create.bind(controller));
    router.get('/clientes', controller.getAll.bind(controller));
    router.get('/clientes/:id', controller.getOneById.bind(controller));
    router.put('/clientes/:id', controller.update.bind(controller));
    router.delete('/clientes/:id', controller.delete.bind(controller));

    return router;
}