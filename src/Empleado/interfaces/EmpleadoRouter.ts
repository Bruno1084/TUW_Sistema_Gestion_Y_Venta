import { Router } from "express";
import { EmpleadoController } from "./EmpleadoController";

export function empleadoRouter(empleadoController: EmpleadoController): Router {
    const router = Router();

    router.post('/create', empleadoController.createEmpleado.bind(empleadoController));
    router.get('/getAll', empleadoController.getAllEmpleado.bind(empleadoController));
    router.get('/getOneById', empleadoController.getOneByIdEmpleado.bind(empleadoController));
    router.post('/update', empleadoController.updateEmpleado.bind(empleadoController));
    router.post('/delete', empleadoController.deleteEmpleado.bind(empleadoController));

    return router;
};
