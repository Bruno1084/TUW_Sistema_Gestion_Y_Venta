import type { UsuarioRepository } from "../domain/UsuarioRepository";
import { UsuarioNombre } from "../domain/UsuarioNombre";
import jwt from  "jsonwebtoken";

export class UsuarioLogin {
    constructor(private repository: UsuarioRepository) { }

    async run(nombre: string, contrasenia: string): Promise<string> {
        const usuario = await this.repository.getOneByNombre(new UsuarioNombre(nombre));
        
        if(!usuario)
            throw new Error('Usuario no encontrado');

        if (!usuario.contrasenia.comparar(contrasenia)) 
            throw new Error("Contraseña inválida");

        const payload = { sub: usuario.id.value };
        return jwt.sign(payload, process.env.JWT_SECRET || "super_secret", { expiresIn: "1h" });
    }
}