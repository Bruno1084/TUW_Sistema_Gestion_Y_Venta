import { Router } from "express";
import type { DetalleCompraController } from "./DetalleCompraController";

export function detalleCompraRouter(detalleCompraController: DetalleCompraController): Router {
    const router = Router();

    router.post('/create', detalleCompraController.createDetalleCompra.bind(detalleCompraController));
    router.get('/getAll', detalleCompraController.getAllDetalleCompra.bind(detalleCompraController));
    router.get('/getAllFromCompraById/:compraId', detalleCompraController.getAllFromCompraByIdDetalleCompra.bind(detalleCompraController));
    router.get('/getOneById/:compraId/:productoCodigoBarra', detalleCompraController.getOneByIdDetalleCompra.bind(detalleCompraController));

    return router;
}