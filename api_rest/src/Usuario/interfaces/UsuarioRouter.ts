import type { Pool } from "mysql2/promise";
import { UsuarioController } from "./UsuarioController";
import { Router } from "express";
import { UsuarioLogin } from "../application/UsuarioLogin";
import { UsuarioRegister } from "../application/UsuarioRegister";
import { MySQLUsuarioRepository } from "../infrastructure/MySQLUsuarioRepository";
import { UsuarioGetOneById } from "../application/UsuarioGetOneById";

export function usuarioRouter(pool: Pool): Router {
    const repo = new MySQLUsuarioRepository(pool);
    const useCases = {
        login: new UsuarioLogin(repo),
        register: new UsuarioRegister(repo),
        getOneById: new UsuarioGetOneById(repo)
    };

    const controller = new UsuarioController(useCases);
    const router = Router();

    router.post('/usuarios/login', controller.login.bind(controller));
    router.post('/usuarios/register', controller.register.bind(controller));
    router.get('/usuarios/:id', controller.getOneById.bind(controller));

    return router;
};
