import type { Router } from "express";
import type { Pool } from "mysql2/promise";
import { UsuarioLogin } from "./application/UsuarioLogin";
import { UsuarioRegister } from "./application/UsuarioRegister";
import { UsuarioGetOneById } from "./application/UsuarioGetOneById";
import { MySQLUsuarioRepository } from "./infrastructure/MySQLUsuarioRepository";
import { UsuarioController } from "./interfaces/UsuarioController";
import { usuarioRouter } from "./interfaces/UsuarioRouter";

export function initUsuarioModule(pool: Pool): Router {
    const repo = new MySQLUsuarioRepository(pool);

    const useCases = {
        login: new UsuarioLogin(repo),
        register: new UsuarioRegister(repo),
        getOneById: new UsuarioGetOneById(repo),
    };

    const controller = new UsuarioController(useCases);
    return usuarioRouter(controller);
}