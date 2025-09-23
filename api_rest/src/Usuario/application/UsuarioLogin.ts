import type { UsuarioRepository } from "../domain/UsuarioRepository";
import { UsuarioNombre } from "../domain/UsuarioNombre";
import jwt from  "jsonwebtoken";

export class UsuarioLogin {
    constructor(private repository: UsuarioRepository) { }

    async run(nombre: string, contrasenia: string): Promise<{token: string, usuario: { id: number, nombre: string }}> {
        const usuario = await this.repository.login(new UsuarioNombre(nombre));
        
        if(!usuario)
            throw new Error('Usuario no encontrado');

        if (!usuario.contrasenia.comparar(contrasenia)) 
            throw new Error("Contraseña inválida");

        const payload = { sub: usuario.id.value, role: "usuario" };
        const token = jwt.sign(payload, process.env.JWT_SECRET || "super_secret", { expiresIn: "2h" });

        return {
            token: token,
            usuario: {
                id: usuario.id.value,
                nombre: usuario.nombre.value
            }
        }
    }
}