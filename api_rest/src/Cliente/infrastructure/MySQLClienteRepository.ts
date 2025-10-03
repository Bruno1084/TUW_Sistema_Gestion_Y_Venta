import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { ClienteRepository } from "../domain/ClienteRepository";
import type { ClienteDTO } from "../application/ClienteDTO";
import { Cliente } from "../domain/Cliente";
import { ClienteId } from "../domain/ClienteId";

type MySQLCliente = {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    es_activo: boolean;
}

export class MySQLClienteRepository implements ClienteRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(cliente: Cliente): Promise<ClienteDTO> {
        const query = `
            INSERT INTO clientes(nombre, direccion, telefono, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            cliente.nombre.value,
            cliente.direccion.value,
            cliente.telefono.value,
            cliente.fechaCreacion.value,
            cliente.fechaModificacion.value,
            cliente.esActivo.value
        ]);

        const clienteId = result.insertId;
        return this.getOneById(new ClienteId(clienteId)) as Promise<ClienteDTO>;
    }

    async getAll(): Promise<ClienteDTO[]> {
        const query = `SELECT * FROM clientes WHERE es_activo = true`;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLCliente)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                nombre: row!.nombre,
                direccion: row!.direccion,
                telefono: row!.telefono,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
                esActivo: row!.es_activo
            })
        );
    }

    async getOneById(clienteId: ClienteId): Promise<ClienteDTO | null> {
        const query = `SELECT * FROM clientes WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLCliente & RowDataPacket)[]>(query, [clienteId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            id: row!.id,
            nombre: row!.nombre,
            direccion: row!.direccion,
            telefono: row!.telefono,
            fechaCreacion: row!.fecha_creacion,
            fechaModificacion: row!.fecha_modificacion,
            esActivo: row!.es_activo
        }
    }

    async update(cliente: Cliente): Promise<ClienteDTO> {
        const query = `
            UPDATE clientes SET
            nombre = ?,
            direccion = ?,
            telefono = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?
            WHERE id = ?
        `;

        await this.pool.query<ResultSetHeader>(query, [
            cliente.nombre.value,
            cliente.direccion.value,
            cliente.telefono.value,
            cliente.fechaCreacion.value,
            cliente.fechaModificacion.value,
            cliente.id.value
        ]);

        return this.getOneById(cliente.id) as Promise<ClienteDTO>;
    }

    async delete(clienteId: ClienteId): Promise<void> {
        const query = `UPDATE clientes SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [clienteId.value]);
    }
}