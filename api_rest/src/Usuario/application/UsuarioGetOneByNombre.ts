import type { UsuarioRepository } from "../domain/UsuarioRepository";
import type { Usuario } from "../domain/Usuario";
import { UsuarioNombre } from "../domain/UsuarioNombre";

export class UsuarioGetOneByNombre {
    constructor(private repository: UsuarioRepository) { }

    async run(nombre: string): Promise<Usuario | null> {
        return await this.repository.getOneByNombre(new UsuarioNombre(nombre));
    }
}