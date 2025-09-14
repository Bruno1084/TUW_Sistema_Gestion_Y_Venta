import { Router } from "express";
import { CajeroController } from "./CajeroController";

export function cajeroRouter(cajeroController: CajeroController): Router {
    const router = Router();

    router.post('/create', cajeroController.createCajero.bind(cajeroController));
    router.get('/getOneById/:id', cajeroController.getOneByIdCajero.bind(cajeroController));
    router.get('/getOneByNombre/:id', cajeroController.getOneByNombreCajero.bind(cajeroController));

    return router;
};
