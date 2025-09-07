import type { Pool } from "mysql2/promise";
import type { ClienteRepository } from "../domain/ClienteRepository";
import { Cliente } from "../domain/Cliente";
import { ClienteId } from "../domain/ClienteId";
import type { RowDataPacket } from "mysql2";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteFechaCreacion } from "../domain/ClienteFechaCreacion";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";
import { ClienteEsActivo } from "../domain/ClienteEsActivo";

type MySQLCliente = {

}

export class MySQLClienteRepository implements ClienteRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(cliente: Cliente): Promise<void> {
        const query = `
            INSERT INTO clientes(nombre, direccion, telefono, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            cliente.nombre.value,
            cliente.direccion.value,
            cliente.telefono.value,
            cliente.fechaCreacion.value,
            cliente.fechaModificacion.value,
            cliente.esActivo.value
        ]);
    }

    async getAll(): Promise<Cliente[]> {
        const query = `SELECT * FROM clientes WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLCliente & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Cliente(
                    new ClienteId(row.id),
                    new ClienteNombre(row.nombre),
                    new ClienteDireccion(row.direccion),
                    new ClienteTelefono(row.telefono),
                    new ClienteFechaCreacion(row.fecha_creacion),
                    new ClienteFechaModificacion(row.fecha_modificacion),
                    new ClienteEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(clienteId: ClienteId): Promise<Cliente | null> {
        const query = `SELECT * FROM clientes WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLCliente & RowDataPacket)[]>(query, [clienteId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Cliente(
            new ClienteId(row!.id),
            new ClienteNombre(row!.nombre),
            new ClienteDireccion(row!.direccion),
            new ClienteTelefono(row!.telefono),
            new ClienteFechaCreacion(row!.fecha_creacion),
            new ClienteFechaModificacion(row!.fecha_modificacion),
            new ClienteEsActivo(row!.es_activo)
        );
    }

    async update(cliente: Cliente): Promise<void> {
        const query = `
            UPDATE clientes SET
            nombre = ?,
            direccion = ?,
            telefono = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            WHERE id = ?
        `;

        await this.pool.query(query, [
            cliente.nombre.value,
            cliente.direccion.value,
            cliente.telefono.value,
            cliente.fechaCreacion.value,
            cliente.fechaModificacion.value,
            cliente.id.value
        ]);
    }

    async delete(clienteId: ClienteId): Promise<void> {
        const query = `UPDATE cliente SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [clienteId]);
    }
}