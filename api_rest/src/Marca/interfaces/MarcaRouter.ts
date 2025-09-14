import { Router } from "express";
import type { MarcaController } from "./MarcaController";

export function marcaRouter(marcaController: MarcaController): Router {
    const router = Router();

    router.post('/create', marcaController.createMarca.bind(marcaController));
    router.get('/getAll', marcaController.getAllMarca.bind(marcaController));
    router.get('/getOneById/:id', marcaController.getOneByIdMarca.bind(marcaController));
    router.put('/update/:id', marcaController.updateMarca.bind(marcaController));
    router.delete('/delete/:id', marcaController.deleteMarca.bind(marcaController));

    return router;
}