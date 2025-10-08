import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { ProveedorRepository } from "../domain/ProveedorRepository";
import type { ProveedorDTO } from "../application/ProveedorDTO";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorId } from "../domain/ProveedorId";

type MySQLProveedor = {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    es_activo: true;
}

export class MySQLProveedorRepository implements ProveedorRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(proveedor: Proveedor): Promise<ProveedorDTO> {
        const query = `
        INSERT INTO proveedores(nombre, direccion, telefono, fecha_creacion, fecha_modificacion)
        VALUES(?, ?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            proveedor.nombre.value,
            proveedor.direccion.value,
            proveedor.telefono.value,
            proveedor.fechaCreacion.value,
            proveedor.fechaModificacion.value,
        ]);

        const proveedorId = result.insertId;
        return this.getOneById(new ProveedorId(proveedorId)) as Promise<ProveedorDTO>;
    }

    async getAll(): Promise<ProveedorDTO[]> {
        const query = `
        SELECT
        nombre, direccion, telefono, fecha_creacion, fecha_modificacion
        FROM proveedores WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLProveedor & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                nombre: row!.nombre,
                direccion: row!.direccion,
                telefono: row!.telefono,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
            })
        );
    }

    async getOneById(proveedorId: ProveedorId): Promise<ProveedorDTO | null> {
        const query = `
        SELECT
        nombre, direccion, telefono, fecha_creacion, fecha_modificacion
        FROM proveedores WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLProveedor & RowDataPacket)[]>(query, [proveedorId.value]);

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
        }
    }

    async update(proveedor: Proveedor): Promise<ProveedorDTO> {
        const query = `
            UPDATE proveedores SET
            nombre = ?,
            direccion = ?,
            telefono = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            WHERE id = ?
        `;

        await this.pool.query(query, [
            proveedor.nombre.value,
            proveedor.direccion.value,
            proveedor.telefono.value,
            proveedor.fechaCreacion.value,
            proveedor.fechaModificacion.value,
            proveedor.id.value
        ]);

        return this.getOneById(proveedor.id) as Promise<ProveedorDTO>;
    }

    async delete(proveedorId: ProveedorId): Promise<void> {
        const query = `UPDATE proveedores es_activo = false WHERE id = ?`;

        await this.pool.query(query, [proveedorId.value]);
    }
}