import type { Pool } from "mysql2/promise";
import { Router } from "express";
import { MySQLVentaRepository } from "../infrastructure/MySQLVentaRepository";
import { VentaCreate } from "../application/VentaCreate";
import { VentaGetAll } from "../application/VentaGetAll";
import { VentaGetOneByIdWithDetail } from "../application/VentaGetOneByIdWithDetail";
import { VentaController } from "./VentaController";
import { VentaGetAllByClientes } from "../application/VentaGetAllByClientes";
import { VentaGetAllByProductos } from "../application/VentaGetAllByProductos";

export function ventaRouter(pool: Pool): Router {
    const repo = new MySQLVentaRepository(pool);
    const useCases = {
        create: new VentaCreate(repo),
        getAll: new VentaGetAll(repo),
        getAllByClientes: new VentaGetAllByClientes(repo),
        getAllByProductos: new VentaGetAllByProductos(repo),
        getOneByIdWithDetail: new VentaGetOneByIdWithDetail(repo)
    };

    const controller = new VentaController(useCases);
    const router = Router();

    router.post('/ventas', controller.create.bind(controller));
    router.get('/ventas', controller.getAll.bind(controller));
    router.get('/ventas/:id', controller.getOneByIdWithDetail.bind(controller));

    // Reportes endpoints
    router.get('/ventas/reportes/clientes/:intervaloFecha', controller.getAllByClientes.bind(controller));
    router.get('/ventas/reportes/productos/:intervaloFecha', controller.getAllByProductos.bind(controller));


    return router;
}