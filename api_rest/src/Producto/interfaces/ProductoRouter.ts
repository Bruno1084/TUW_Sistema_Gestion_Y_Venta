import { Router } from "express";
import { ProductoController } from "./ProductoController";

export function productoRouter(productoController: ProductoController): Router {
    const router = Router();

    router.post('/create', productoController.createProducto.bind(productoController));
    router.get('/getAll', productoController.getAllProducto.bind(productoController));
    router.get('/getOneById', productoController.getOneByIdProducto.bind(productoController));
    router.post('/update', productoController.updateProducto.bind(productoController));

    return router;
}