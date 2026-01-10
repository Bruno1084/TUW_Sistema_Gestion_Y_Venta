import type { UsuarioRepository } from "../domain/UsuarioRepository";
import type { Usuario } from "../domain/Usuario";
import { UsuarioId } from "../domain/UsuarioId";

export class UsuarioGetOneById {
    constructor(private repository: UsuarioRepository) { }

    async run(id: number): Promise<Usuario | null> {
        const usuario = await this.repository.getOneById(new UsuarioId(id));

        if (!usuario) {
            throw new Error('Usuario no encontrado');
        }

        return usuario;
    }
}