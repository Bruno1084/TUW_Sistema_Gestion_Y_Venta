import type { Usuario } from "./Usuario";
import type { UsuarioContrasenia } from "./UsuarioContrasenia";
import type { UsuarioId } from "./UsuarioId";
import type { UsuarioNombre } from "./UsuarioNombre";

export interface UsuarioRepository {
    register(usuario: Usuario): Promise<Usuario>;
    login(nombre: string, contrasenia: string): Promise<string>;
    getOneById(usuarioId: UsuarioId): Promise<Usuario>;
    getOneByNombre(usuarioNombre: UsuarioNombre): Promise<Usuario>;
}