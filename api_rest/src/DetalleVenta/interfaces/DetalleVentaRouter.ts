import { Router } from "express";
import type { DetalleVentaController } from "./DetalleVentaController";

export function detalleVentaRouter(detalleVentaController: DetalleVentaController): Router {
    const router = Router();

    router.post('/create', detalleVentaController.createDetalleVenta.bind(detalleVentaController));
    router.post('/createMany', detalleVentaController.createManyDetalleVenta.bind(detalleVentaController));
    router.get('/getAll', detalleVentaController.getAllDetalleVenta.bind(detalleVentaController));
    router.get('/getAllFromVentaById/:ventaId', detalleVentaController.getAllFromVentaByIdDetalleVenta.bind(detalleVentaController));
    router.get('/getOneById/:ventaId/:productoCodigoBarra', detalleVentaController.getOneByIdDetalleVenta.bind(detalleVentaController));

    return router;
}