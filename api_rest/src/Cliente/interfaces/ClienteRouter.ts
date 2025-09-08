import { Router } from "express";
import { ClienteController } from "./ClienteController";

export function clienteRouter(clienteController: ClienteController): Router {
    const router = Router();

    router.post('/create', clienteController.createCliente.bind(clienteController));
    router.get('/getAll', clienteController.getAllCliente.bind(clienteController));
    router.get('/getOneById', clienteController.getOneByIdCliente.bind(clienteController));
    router.post('/update', clienteController.updateCliente.bind(clienteController));
    router.post('/delete', clienteController.deleteCliente.bind(clienteController));

    return router;
}