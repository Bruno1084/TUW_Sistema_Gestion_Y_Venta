import type { Usuario } from "./Usuario";
import type { UsuarioId } from "./UsuarioId";
import type { UsuarioNombre } from "./UsuarioNombre";

export interface UsuarioRepository {
    register(usuario: Usuario): Promise<Usuario>;
    login(usuarioNombre: UsuarioNombre): Promise<Usuario>;
    getOneById(usuarioId: UsuarioId): Promise<Usuario>;
}