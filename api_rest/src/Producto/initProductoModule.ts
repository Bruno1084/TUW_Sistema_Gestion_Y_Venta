import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { MySQLProductoRepository } from "./infrastructure/MySQLProductoRepository";
import { ProductoCreate } from "./application/ProductoCreate";
import { ProductoGetAll } from "./application/ProductoGetAll";
import { ProductoGetAllWithDetail } from "./application/ProductoGetAllWithDetail";
import { ProductoGetOneById } from "./application/ProductoGetOneById";
import { ProductoUpdate } from "./application/ProductoUpdate";
import { ProductoDelete } from "./application/ProductoDelete";
import { ProductoController } from "./interfaces/ProductoController";
import { productoRouter } from "./interfaces/ProductoRouter";

// Dependencias
import type { MySQLProveedorRepository } from "../Proveedor/infrastructure/MySQLProveedorRepository";
import type { MySQLMarcaRepository } from "../Marca/infrastructure/MySQLMarcaRepository";
import type { MySQLRubroRepository } from "../Rubro/infrastructure/MySQLRubroRepository";


export function initProductoModule(
    pool: Pool,
    proveedorRepo: MySQLProveedorRepository,
    marcaRepo: MySQLMarcaRepository,
    rubroRepo: MySQLRubroRepository
): Router {
    const repo = new MySQLProductoRepository(pool);

    const useCases = {
        create: new ProductoCreate(repo),
        getAll: new ProductoGetAll(repo),
        getAllWithDetail: new ProductoGetAllWithDetail(repo),
        getOneById: new ProductoGetOneById(repo),
        update: new ProductoUpdate(repo, proveedorRepo, marcaRepo, rubroRepo),
        delete: new ProductoDelete(repo)
    };

    const controller = new ProductoController(useCases);
    return productoRouter(controller);
}