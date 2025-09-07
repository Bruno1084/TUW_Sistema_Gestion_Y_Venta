import { Router } from "express";
import type { MarcaController } from "./MarcaController";

export function marcaRouter(marcaController: MarcaController): Router {
    const router = Router();

    router.post('/create', marcaController.createMarca.bind(marcaController));
    router.get('/getAll', marcaController.getAllMarca.bind(marcaController));
    router.get('/getOneById', marcaController.getOneByIdMarca.bind(marcaController));
    router.post('/update', marcaController.updateMarca.bind(marcaController));
    router.post('/delete', marcaController.deleteMarca.bind(marcaController));

    return router;
}