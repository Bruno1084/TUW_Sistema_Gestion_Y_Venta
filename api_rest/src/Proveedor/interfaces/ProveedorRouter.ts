import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { ProveedorController } from "./ProveedorController";
import { MySQLProveedorRepository } from "../infrastructure/MySQLProveedorRepository";
import { ProveedorCreate } from "../application/ProveedorCreate";
import { ProveedorGetAll } from "../application/ProveedorGetAll";
import { ProveedorGetOneById } from "../application/ProveedorGetOneById";
import { ProveedorUpdate } from "../application/ProveedorUpdate";
import { ProveedorDelete } from "../application/ProveedorDelete";
import { ProveedorFindByNames } from "../application/ProveedorFindByNames";

export function proveedorRouter(pool: Pool): Router {
    const repo = new MySQLProveedorRepository(pool);
    const useCases = {
        create: new ProveedorCreate(repo),
        getAll: new ProveedorGetAll(repo),
        getOneById: new ProveedorGetOneById(repo),
        update: new ProveedorUpdate(repo),
        delete: new ProveedorDelete(repo),
        findByNames: new ProveedorFindByNames(repo)
    };

    const controller = new ProveedorController(useCases);
    const router = Router();

    router.post('/proveedores', controller.create.bind(controller));
    router.get('/proveedores', controller.getAll.bind(controller));
    router.get('/proveedores/:id', controller.getOneById.bind(controller));
    router.put('/proveedores/:id', controller.update.bind(controller));
    router.delete('/proveedores/:id', controller.delete.bind(controller));
    router.post('/proveedores/names', controller.findByNames.bind(controller));

    return router;
}