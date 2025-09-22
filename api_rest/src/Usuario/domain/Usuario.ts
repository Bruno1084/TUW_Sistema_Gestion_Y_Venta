import type { UsuarioId } from "./UsuarioId";
import { UsuarioNombre } from "./UsuarioNombre";
import { UsuarioContrasenia } from "./UsuarioContrasenia";

export class Usuario {
    id: UsuarioId;
    nombre: UsuarioNombre;
    contrasenia: UsuarioContrasenia;

    constructor(id: UsuarioId, nombre: UsuarioNombre, contrasenia: UsuarioContrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }

    toJSON() {
        return {
            id: this.id.value,
            nombre: this.nombre.value,
            contrasenia: this.contrasenia.value
        }
    }
}