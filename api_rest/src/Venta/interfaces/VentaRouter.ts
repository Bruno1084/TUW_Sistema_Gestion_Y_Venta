import { Router } from "express";

export function ventaRouter(ventaController: VentaController): Router{
    const router = Router();

    router.post('/create', ventaController.createVenta.bind(ventaController));
    router.get('/getAll', ventaController.getAll.bind(ventaController));
    router.get('/getOneById', ventaController.getOneById.bind(ventaController));

    return router;
}