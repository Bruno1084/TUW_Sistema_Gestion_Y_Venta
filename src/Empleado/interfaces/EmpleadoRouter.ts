import { Router } from "express";
import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import { EmpleadoController } from "./EmpleadoController";

export function empleadoRouter(empleadoRepository: EmpleadoRepository): Router {
    const router = Router();
    const controller = new EmpleadoController(empleadoRepository);

    router.post('/create', controller.createEmpleado.bind(controller));
    router.get('/getAll', controller.getAllEmpleado.bind(controller));

    return router;
};
