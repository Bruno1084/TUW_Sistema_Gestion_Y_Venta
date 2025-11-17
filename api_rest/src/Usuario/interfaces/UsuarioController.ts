import type { Request, Response } from "express"
import type { UsuarioGetOneById } from "../application/UsuarioGetOneById"
import type { UsuarioLogin } from "../application/UsuarioLogin"
import type { UsuarioRegister } from "../application/UsuarioRegister"

type UsuarioUseCases = {
    register: UsuarioRegister,
    login: UsuarioLogin,
    getOneById: UsuarioGetOneById,
}

export class UsuarioController {
    constructor(private useCases: UsuarioUseCases) { }

    async register(req: Request, res: Response): Promise<void> {
        try {
            const {
                nombre,
                contrasenia
            } = req.body;

            const usuarioCreado = await this.useCases.register.run(nombre, contrasenia);

            const response = {
                id: usuarioCreado.id.value,
                nombre: usuarioCreado.nombre.value
            }
            res.status(201).json(response);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async login(req: Request, res: Response): Promise<void> {
        try {
            const { nombre, contrasenia } = req.body;

            const result = await this.useCases.login.run(nombre, contrasenia);
            
            res.status(200).json(result);
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    }

    async getOneById(req: Request, res: Response): Promise<void> {
        try {
            const { id } = req.params;
            const usuario = await this.useCases.getOneById.run(Number(id));

            const response = {
                id: usuario?.id.value,
                nombre: usuario?.nombre.value
            }

            res.status(200).json(response);
        } catch (err: any) {
            res.status(500).json({ error: err.message });
        }
    }
}