import type { Pool } from "mysql2/promise";
import type { Router } from "express";
import { MySQLClienteRepository } from "./infrastructure/MySQLClienteRepository";
import { ClienteCreate } from "./application/ClienteCreate";
import { ClienteGetAll } from "./application/ClienteGetAll";
import { ClienteGetOneById } from "./application/ClienteGetOneById";
import { ClienteUpdate } from "./application/ClienteUpdate";
import { ClienteDelete } from "./application/ClienteDelete";
import { ClienteController } from "./interfaces/ClienteController";
import { clienteRouter } from "./interfaces/ClienteRouter";

export function initClienteModule(pool: Pool): Router {
    const repo = new MySQLClienteRepository(pool);

    const useCases = {
        create: new ClienteCreate(repo),
        getAll: new ClienteGetAll(repo),
        getOneById: new ClienteGetOneById(repo),
        update: new ClienteUpdate(repo),
        delete: new ClienteDelete(repo)
    };

    const controller = new ClienteController(useCases);
    return clienteRouter(controller)
}