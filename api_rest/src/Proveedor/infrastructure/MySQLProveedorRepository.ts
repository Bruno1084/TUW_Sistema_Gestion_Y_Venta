import type { Pool, RowDataPacket } from "mysql2/promise";
import type { ProveedorRepository } from "../domain/ProveedorRepository";
import { Proveedor } from "../domain/Proveedor";
import { ProveedorId } from "../domain/ProveedorId";
import { ProveedorNombre } from "../domain/ProveedorNombre";
import { ProveedorDireccion } from "../domain/ProveedorDireccion";
import { ProveedorTelefono } from "../domain/ProveedorTelefono";
import { ProveedorFechaCreacion } from "../domain/ProveedorFechaCreacion";
import { ProveedorFechaModificacion } from "../domain/ProveedorFechaModificacion";
import { ProveedorEsActivo } from "../domain/ProveedorEsActivo";

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

    async create(proveedor: Proveedor): Promise<void> {
        const query = `
        INSERT INTO proveedores(nombre, direccion, telefono, fechaCreacion, fechaModificacion, es_activo)
        VALUES(?, ?, ?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            proveedor.nombre.value,
            proveedor.direccion.value,
            proveedor.telefono.value,
            proveedor.fechaCreacion.value,
            proveedor.fechaModificacion.value,
            proveedor.esActivo.value
        ]);
    }

    async getAll(): Promise<Proveedor[]> {
        const query = `SELECT * FROM proveedores WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLProveedor & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Proveedor(
                    new ProveedorId(row.id),
                    new ProveedorNombre(row.nombre),
                    new ProveedorDireccion(row.direccion),
                    new ProveedorTelefono(row.telefono),
                    new ProveedorFechaCreacion(row.fecha_creacion),
                    new ProveedorFechaModificacion(row.fecha_modificacion),
                    new ProveedorEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(proveedorId: ProveedorId): Promise<Proveedor | null> {
        const query = `SELECT * FROM proveedores WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLProveedor & RowDataPacket)[]>(query, [proveedorId.value]);

        if (rows.length === 0) {
            return null;
        }

        return new Proveedor(
            new ProveedorId(rows[0]!.id),
            new ProveedorNombre(rows[0]!.nombre),
            new ProveedorDireccion(rows[0]!.direccion),
            new ProveedorTelefono(rows[0]!.telefono),
            new ProveedorFechaCreacion(rows[0]!.fecha_creacion),
            new ProveedorFechaModificacion(rows[0]!.fecha_modificacion),
            new ProveedorEsActivo(rows[0]!.es_activo)
        );
    }

    async update(proveedor: Proveedor): Promise<void> {
        const query = `
            UPDATE proveedores SET
            nombre = ?,
            direccion = ?,
            telefono = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            es_activo = ?
            WHERE id = ?
        `;

        await this.pool.query(query, [
            proveedor.nombre.value,
            proveedor.direccion.value,
            proveedor.direccion.value,
            proveedor.telefono.value,
            proveedor.fechaCreacion.value,
            proveedor.fechaModificacion.value,
            proveedor.esActivo.value
        ]);
    }

    async delete(proveedorId: ProveedorId): Promise<void> {
        const query = `UPDATE proveedores es_activo = false WHERE id = ?`;

        await this.pool.query(query, [proveedorId.value]);
    }
}