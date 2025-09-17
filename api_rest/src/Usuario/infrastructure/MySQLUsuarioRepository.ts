import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { UsuarioRepository } from "../domain/UsuarioRepository";
import { Usuario } from "../domain/Usuario";
import { UsuarioNombre } from "../domain/UsuarioNombre";
import { UsuarioId } from "../domain/UsuarioId";
import { UsuarioContrasenia } from "../domain/UsuarioContrasenia";
import jwt from "jsonwebtoken";

type MySQLUsuario = {
    id: number;
    nombre: string;
    contrasenia_hash: string;
    contrasenia_salt: string;
}

export class MySQLUsuarioRepository implements UsuarioRepository {
    private pool: Pool

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async register(usuario: Usuario): Promise<Usuario> {
        const query = `
        INSERT INTO usuarios(nombre, contrasenia_hash, contrasenia_salt)
        VALUES(?, ?, ?)
        `

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            usuario.nombre.value,
            usuario.contrasenia.value.hash,
            usuario.contrasenia.value.salt
        ]);

        const usuarioId = result.insertId;
        return await this.getOneById(new UsuarioId(usuarioId));
    }

    async login(nombre: string, contrasenia: string): Promise<string> {
        const query = `SELECT * from usuarios WHERE nombre = ?`;

        const [rows] = await this.pool.query<(MySQLUsuario & RowDataPacket)[]>(query, [nombre]);

        if (rows.length === 0) throw Error('Usuario no encontrado');

        const usuarioRow = rows[0];
        const convertedContrasenia = UsuarioContrasenia.fromHashed(
            usuarioRow!.contrasenia_hash,
            usuarioRow!.contrasenia_salt
        );

        if (!convertedContrasenia.comparar(contrasenia)) {
            throw new Error("Contraseña inválida");
        }

        const payload = { sub: usuarioRow!.id, role: "usuario" };
        const token = jwt.sign(payload, process.env.JWT_SECRET || "super_secret", {
            expiresIn: "1h",
        });

        return token;
    }

    async getOneById(usuarioId: UsuarioId): Promise<Usuario> {
        const query = `SELECT * FROM usuarios WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLUsuario & RowDataPacket)[]>(query, [usuarioId.value]);

        if (rows.length === 0) throw new Error('Usuario no encontrado');

        const row = rows[0];
        return new Usuario(
            new UsuarioId(row!.id),
            new UsuarioNombre(row!.nombre),
            UsuarioContrasenia.fromHashed(row!.contrasenia_hash, row!.contrasenia_salt)
        );
    }

    async getOneByNombre(usuarioNombre: UsuarioNombre): Promise<Usuario> {
        const query = `SELECT * FROM usuarios WHERE nombre = ?`;

        const [rows] = await this.pool.query<(MySQLUsuario & RowDataPacket)[]>(query, [usuarioNombre.value]);

        if (rows.length === 0) throw new Error('Usuario no encontrado');

        const row = rows[0];
        return new Usuario(
            new UsuarioId(row!.id),
            new UsuarioNombre(row!.nombre),
            UsuarioContrasenia.fromHashed(row!.contrasenia_hash, row!.contrasenia_salt)
        );
    }
}