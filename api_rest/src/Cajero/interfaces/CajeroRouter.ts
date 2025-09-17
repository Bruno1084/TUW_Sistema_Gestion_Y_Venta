import { Router } from "express";
import { CajeroController } from "./CajeroController";
import { authMiddleware } from "../../middlewares/authMiddleware";

export function cajeroRouter(cajeroController: CajeroController): Router {
    const router = Router();
    //Rutas públicas
    router.post('/login', cajeroController.loginCajero.bind(cajeroController));
    router.post('/create', cajeroController.createCajero.bind(cajeroController));

    router.use(authMiddleware);

    //Rutas protegídas
    router.get('/getOneById/:id', cajeroController.getOneByIdCajero.bind(cajeroController));
    router.get('/getOneByNombre/:id', cajeroController.getOneByNombreCajero.bind(cajeroController));

    return router;
};
