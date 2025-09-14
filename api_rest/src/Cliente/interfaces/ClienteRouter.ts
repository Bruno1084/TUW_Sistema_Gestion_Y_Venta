import { Router } from "express";
import { ClienteController } from "./ClienteController";

export function clienteRouter(clienteController: ClienteController): Router {
    const router = Router();

    router.post('/create', clienteController.createCliente.bind(clienteController));
    router.get('/getAll', clienteController.getAllCliente.bind(clienteController));
    router.get('/getOneById/:id', clienteController.getOneByIdCliente.bind(clienteController));
    router.put('/update/:id', clienteController.updateCliente.bind(clienteController));
    router.delete('/delete/:id', clienteController.deleteCliente.bind(clienteController));

    return router;
}