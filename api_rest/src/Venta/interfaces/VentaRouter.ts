import { Router } from "express";
import type { VentaController } from "./VentaController";

export function ventaRouter(ventaController: VentaController): Router{
    const router = Router();

    router.post('/create', ventaController.createVenta.bind(ventaController));
    router.get('/getAll', ventaController.getAllVenta.bind(ventaController));
    router.get('/getOneById/:id', ventaController.getOneByIdVenta.bind(ventaController));

    return router;
}