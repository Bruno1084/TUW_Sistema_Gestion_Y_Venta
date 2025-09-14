import { Router } from "express";
import { EmpleadoController } from "./EmpleadoController";

export function empleadoRouter(empleadoController: EmpleadoController): Router {
    const router = Router();

    router.post('/create', empleadoController.createEmpleado.bind(empleadoController));
    router.get('/getAll', empleadoController.getAllEmpleado.bind(empleadoController));
    router.get('/getOneById/:id', empleadoController.getOneByIdEmpleado.bind(empleadoController));
    router.put('/update/:id', empleadoController.updateEmpleado.bind(empleadoController));
    router.delete('/delete/:id', empleadoController.deleteEmpleado.bind(empleadoController));

    return router;
};
