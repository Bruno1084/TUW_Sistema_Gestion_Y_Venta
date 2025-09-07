import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLProveedorRepository } from "./infrastructure/MySQLProveedorRepository";
import { ProveedorCreate } from "./application/ProveedorCreate";
import { ProveedorGetAll } from "./application/ProveedorGetAll";
import { ProveedorGetOneById } from "./application/ProveedorGetOneById";
import { ProveedorUpdate } from "./application/ProveedorUpdate";
import { ProveedorDelete } from "./application/ProveedorDelete";
import { ProveedorController } from "./interfaces/ProveedorController";
import { proveedorRouter } from "./interfaces/ProveedorRouter";

export function initProveedorModule(pool: Pool): Router {
    const repo = new MySQLProveedorRepository(pool);
    
    const useCases = {
        create: new ProveedorCreate(repo),
        getAll: new ProveedorGetAll(repo),
        getOneById: new ProveedorGetOneById(repo),
        update: new ProveedorUpdate(repo),
        delete: new ProveedorDelete(repo)
    };

    const controller = new ProveedorController(useCases);
    return proveedorRouter(controller);
}