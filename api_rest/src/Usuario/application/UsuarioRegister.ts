import type { UsuarioRepository } from "../domain/UsuarioRepository";
import { Usuario } from "../domain/Usuario";
import { UsuarioContrasenia } from "../domain/UsuarioContrasenia";
import { UsuarioId } from "../domain/UsuarioId";
import { UsuarioNombre } from "../domain/UsuarioNombre";

export class UsuarioRegister {
    constructor(private repository: UsuarioRepository) { }

    async run(nombre: string, contrasenia: string): Promise<Usuario> {
        const usuario = new Usuario(
            new UsuarioId(0),
            new UsuarioNombre(nombre),
            new UsuarioContrasenia(contrasenia)
        );

        return await this.repository.register(usuario);
    }
}