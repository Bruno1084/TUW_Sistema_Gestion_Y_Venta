import { Router } from "express";
import { CajeroController } from "./CajeroController";

export function cajeroRouter(cajeroController: CajeroController): Router {
    const router = Router();

    router.post('/create', cajeroController.createCajero.bind(cajeroController));
    router.get('/getOneById', cajeroController.getOneByIdCajero.bind(cajeroController));
    router.get('/getOneByNombre', cajeroController.getOneByNombreCajero.bind(cajeroController));

    return router;
};
