import type { Request, Response } from "express"
import type { UsuarioGetOneById } from "../application/UsuarioGetOneById"
import type { UsuarioGetOneByNombre } from "../application/UsuarioGetOneByNombre"
import type { UsuarioLogin } from "../application/UsuarioLogin"
import type { UsuarioRegister } from "../application/UsuarioRegister"

type UsuarioUseCases = {
    register: UsuarioRegister,
    login: UsuarioLogin,
    getOneById: UsuarioGetOneById,
    getOneByNombre: UsuarioGetOneByNombre
}

export class UsuarioController {
    constructor(private useCases: UsuarioUseCases) { }

    async registerUsuario(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                contrasenia
            } = req.body;

            const usuarioCreado = await this.useCases.register.run(nombre, contrasenia);
            res.status(201).json(usuarioCreado);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async loginUsuario(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                contrasenia
            } = req.body;

            const token = await this.useCases.login.run(nombre, contrasenia);
            res.status(200).json(token);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByIdUsuario(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;

            const usuario = await this.useCases.getOneById.run(Number(id));
            res.status(200).json(usuario);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }

    async getOneByNombreUsuario(req: Request, res: Response): Promise<void> {
        try {
            const { nombre } = req.params;

            const usuario = await this.useCases.getOneByNombre.run(nombre!);
            res.status(200).json(usuario);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }
}