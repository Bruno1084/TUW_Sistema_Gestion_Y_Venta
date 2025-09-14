import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { CajeroRepository } from "../domain/CajeroRepository";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import { EmpleadoDireccion } from "../../Empleado/domain/EmpleadoDireccion";
import { EmpleadoTelefono } from "../../Empleado/domain/EmpleadoTelefono";
import { EmpleadoFechaCreacion } from "../../Empleado/domain/EmpleadoFechaCreacion";
import { EmpleadoFechaModificacion } from "../../Empleado/domain/EmpleadoFechaModificacion";
import { EmpleadoEsActivo } from "../../Empleado/domain/EmpleadoEsActivo";
import { CajeroContrasenia } from "../domain/CajeroContrasenia";
import { Cajero } from "../domain/Cajero";

type MySQLCajero = {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    es_activo: boolean;
    contrasenia: string
};

export class MySQLCajeroRepository implements CajeroRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(cajero: Cajero): Promise<void> {
        const conn = await this.pool.getConnection();

        try {
            await conn.beginTransaction();

            // Insertar empleado
            const [empleado] = await conn.query<ResultSetHeader>(`
                INSERT INTO empleados (nombre, direccion, telefono, fecha_creacion, fecha_modificacion, es_activo)
                VALUES (?, ?, ?, ?, ?, ?)`, [
                cajero.nombre.value,
                cajero.direccion.value,
                cajero.telefono.value,
                cajero.fechaCreacion.value,
                cajero.fechaModificacion.value,
                cajero.esActivo.value
            ]
            );

            const empleadoId = empleado.insertId;

            // Insertar cajero
            await conn.query(`INSERT INTO cajeros (id_empleado, contrasenia) VALUES(?, ?)`, [
                empleadoId,
                cajero.contrasenia.value
            ]);

            await conn.commit();
        } catch (err: any) {
            await conn.rollback();
            throw err;
        } finally {
            conn.release();
        }
    }

    async getOneById(empleadoId: EmpleadoId): Promise<Cajero | null> {
        const query = `
            SELECT e.id, e.nombre, e.direccion, e.telefono,
                e.fecha_creacion, e.fecha_modificacion, e.es_activo,
                c.contrasenia
            FROM empleados e
            INNER JOIN cajeros c ON e.id = c.id_empleado
            WHERE e.id = ?
        `;
        const [rows] = await this.pool.query<(MySQLCajero & RowDataPacket)[]>(query, [empleadoId.value]);

        if (rows.length === 0) return null;

        const row = rows[0];
        return new Cajero(
            new EmpleadoId(row!.id),
            new EmpleadoNombre(row!.nombre),
            new EmpleadoDireccion(row!.direccion),
            new EmpleadoTelefono(row!.telefono),
            new EmpleadoFechaCreacion(row!.fecha_creacion),
            new EmpleadoFechaModificacion(row!.fecha_modificacion),
            new EmpleadoEsActivo(row!.es_activo),
            new CajeroContrasenia(row!.contrasenia)
        );
    }

    async getOneByNombre(nombre: EmpleadoNombre): Promise<Cajero | null> {
        const query = `
            SELECT e.id, e.nombre, e.direccion, e.telefono,
               e.fecha_creacion, e.fecha_modificacion, e.es_activo,
               c.contrasenia
            FROM empleados e
            INNER JOIN cajeros c ON e.id = c.id_empleado
            WHERE e.nombre = ?
        `;
        const [rows] = await this.pool.execute<(MySQLCajero & RowDataPacket)[]>(query, [nombre.value]);

        if (rows.length === 0) return null;

        const row = rows[0];
        return new Cajero(
            new EmpleadoId(row!.id),
            new EmpleadoNombre(row!.nombre),
            new EmpleadoDireccion(row!.direccion),
            new EmpleadoTelefono(row!.telefono),
            new EmpleadoFechaCreacion(row!.fecha_creacion),
            new EmpleadoFechaModificacion(row!.fecha_modificacion),
            new EmpleadoEsActivo(row!.es_activo),
            new CajeroContrasenia(row!.contrasenia)
        );
    }
}
