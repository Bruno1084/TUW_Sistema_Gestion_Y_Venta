import { Router } from "express";
import { ProveedorController } from "./ProveedorController";

export function proveedorRouter(proveedorController: ProveedorController): Router {
    const router = Router();

    router.post('/create', proveedorController.createProveedor.bind(proveedorController));
    router.get('/getAll', proveedorController.getAllProveedor.bind(proveedorController));
    router.get('/getOneById/:id', proveedorController.getOneByIdProveedor.bind(proveedorController));
    router.put('/update/:id', proveedorController.updateProveedor.bind(proveedorController));
    router.delete('/delete/:id', proveedorController.deleteProveedor.bind(proveedorController));

    return router;
}