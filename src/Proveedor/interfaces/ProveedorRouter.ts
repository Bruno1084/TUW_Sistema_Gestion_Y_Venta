import { Router } from "express";
import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { ProveedorController } from "./ProveedorController";

export function ProveedorRouter(proveedorRepository: ProveedorRepository): Router {
    const router = Router();
    const controller = new ProveedorController(proveedorRepository);

    router.post('/create', controller.createProveedor.bind(controller));
    router.get('/getAll', controller.getAllProveedor.bind(controller));
    router.get('/getOneById', controller.getOneByIdProveedor.bind(controller));
    router.post('/update', controller.updateProveedor.bind(controller));
    router.post('/delete', controller.deleteProveedor.bind(controller));

    return router;
}