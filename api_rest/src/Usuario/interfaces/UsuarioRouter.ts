import type { UsuarioController } from "./UsuarioController";
import { Router } from "express";

export function usuarioRouter(usuarioController: UsuarioController): Router {
    const router = Router();

    router.post('/login', usuarioController.loginUsuario.bind(usuarioController));
    router.post('/register', usuarioController.registerUsuario.bind(usuarioController));
    router.get('/getOneById/:id', usuarioController.getOneByIdUsuario.bind(usuarioController));
    router.get('/getOneByNombre/:nombre', usuarioController.getOneByNombreUsuario.bind(usuarioController));

    return router;
};
