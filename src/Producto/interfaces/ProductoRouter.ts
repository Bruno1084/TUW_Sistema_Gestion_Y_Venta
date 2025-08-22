import { Router } from "express";
import type { ProductoRepository } from "../domain/ProductoRepository";
import { ProductoController } from "./ProductoController";

export function ProductoRouter(productoRepository: ProductoRepository): Router {
    const router = Router();
    const controller = new ProductoController(productoRepository);

    router.post('/create', controller.createProducto.bind(controller));
    router.get('/getAll', controller.getAllProducto.bind(controller));

    return router;
}