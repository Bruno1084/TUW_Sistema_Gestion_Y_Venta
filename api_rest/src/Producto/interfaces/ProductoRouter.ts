import type { Pool } from "mysql2/promise";
import type { MySQLProveedorRepository } from "../../Proveedor/infrastructure/MySQLProveedorRepository";
import type { MySQLMarcaRepository } from "../../Marca/infrastructure/MySQLMarcaRepository";
import type { MySQLRubroRepository } from "../../Rubro/infrastructure/MySQLRubroRepository";
import { Router } from "express";
import { ProductoController } from "./ProductoController";
import { MySQLProductoRepository } from "../infrastructure/MySQLProductoRepository";
import { ProductoCreate } from "../application/ProductoCreate";
import { ProductoGetAll } from "../application/ProductoGetAll";
import { ProductoGetAllWithDetail } from "../application/ProductoGetAllWithDetail";
import { ProductoGetOneById } from "../application/ProductoGetOneById";
import { ProductoGetOneByIdWithDetail } from "../application/ProductoGetOneByIdWithDetail";
import { ProductoUpdate } from "../application/ProductoUpdate";
import { ProductoDelete } from "../application/ProductoDelete";
import { ProductoImportXlsx } from "../application/ProductoImportXlsx";
import multer from "multer";

export function productoRouter(
    pool: Pool,
    proveedorRepo: MySQLProveedorRepository,
    marcaRepo: MySQLMarcaRepository,
    rubroRepo: MySQLRubroRepository
): Router {
    const repo = new MySQLProductoRepository(pool);
    const upload = multer({ storage: multer.memoryStorage() });

    const useCases = {
        create: new ProductoCreate(repo),
        getAll: new ProductoGetAll(repo),
        getAllWithDetail: new ProductoGetAllWithDetail(repo),
        getOneById: new ProductoGetOneById(repo),
        getOneByIdWithDetail: new ProductoGetOneByIdWithDetail(repo),
        update: new ProductoUpdate(repo, proveedorRepo, marcaRepo, rubroRepo),
        delete: new ProductoDelete(repo),
        importXlsx: new ProductoImportXlsx(repo)
    };

    const controller = new ProductoController(useCases);
    const router = Router();

    router.post('/productos', controller.create.bind(controller));
    router.get('/productos', controller.getAll.bind(controller));
    router.get('/productos/detail', controller.getAllProductoWithDetail.bind(controller));
    router.get('/productos/:codigo', controller.getOneById.bind(controller));
    router.get('/productos/detail/:codigo', controller.getOneByIdWithDetail.bind(controller));
    router.put('/productos/:codigo', controller.update.bind(controller));
    router.delete('/productos/:codigo', controller.delete.bind(controller));
    router.post('/productos/importXlsx', upload.single('Lista_Productos') , controller.importXlsx.bind(controller));

    return router;
}