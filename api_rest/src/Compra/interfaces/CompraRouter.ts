import { Router } from "express";
import { CompraController } from "./CompraController";

export function compraRouter(compraController: CompraController): Router {
    const router = Router();

    router.post('/create', compraController.createCompra.bind(compraController));
    router.get('/getAll', compraController.getAllCompra.bind(compraController));
    router.get('/getAllWithDetail', compraController.getAllWithDetail.bind(compraController));
    router.get('/getOneById/:id', compraController.getOneByIdCompra.bind(compraController));
    router.get('/getOneByIdWithDetail/:id', compraController.getOneByIdWithDetailCompra.bind(compraController));

    return router;
}