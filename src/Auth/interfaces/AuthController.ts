import type { Request, Response } from "express";
import { AuthService } from "../application/AuthService";

export class AuthController {
    constructor(private authService: AuthService) { }

    async register(req: Request, res: Response): Promise<void> {
        try {
            const { empleado, contrasenia } = req.body;
            await this.authService.register({
                empleado,
                contraseniaPlano: contrasenia,
            });
            res.status(201).json({ message: "Cajero registrado" });
        } catch (err: any) {
            res.status(400).json({ error: err.message });
        }
    };

    async login(req: Request, res: Response): Promise<void> {
        try {
            const { nombre, contrasenia } = req.body;
            const token = await this.authService.login(nombre, contrasenia);
            res.status(200).json({ token });
        } catch (err: any) {
            res.status(401).json({ error: err.message });
        }
    };
}
