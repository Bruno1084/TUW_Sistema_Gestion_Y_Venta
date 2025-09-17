import type { UsuarioRepository } from "../domain/UsuarioRepository";
import type { Usuario } from "../domain/Usuario";
import { UsuarioNombre } from "../domain/UsuarioNombre";

export class UsuarioGetOneByNombre {
    constructor(private repository: UsuarioRepository) { }

    async run(nombre: string): Promise<Usuario | null> {
        const usuario = await this.repository.getOneByNombre(new UsuarioNombre(nombre));

        if(!usuario) throw new Error('Usuario no encontrado');

        return usuario;
    }
}