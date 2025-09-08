import { Router } from "express";
import { ProveedorController } from "./ProveedorController";

export function proveedorRouter(proveedorController: ProveedorController): Router {
    const router = Router();

    router.post('/create', proveedorController.createProveedor.bind(proveedorController));
    router.get('/getAll', proveedorController.getAllProveedor.bind(proveedorController));
    router.get('/getOneById', proveedorController.getOneByIdProveedor.bind(proveedorController));
    router.post('/update', proveedorController.updateProveedor.bind(proveedorController));
    router.post('/delete', proveedorController.deleteProveedor.bind(proveedorController));

    return router;
}