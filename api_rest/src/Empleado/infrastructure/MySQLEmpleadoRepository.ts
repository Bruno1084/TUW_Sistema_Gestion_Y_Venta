import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import { Empleado } from "../domain/Empleado";
import { EmpleadoId } from "../domain/EmpleadoId";
import { EmpleadoNombre } from "../domain/EmpleadoNombre";
import { EmpleadoDireccion } from "../domain/EmpleadoDireccion";
import { EmpleadoTelefono } from "../domain/EmpleadoTelefono";
import { EmpleadoFechaCreacion } from "../domain/EmpleadoFechaCreacion";
import { EmpleadoFechaModificacion } from "../domain/EmpleadoFechaModificacion";
import { EmpleadoEsActivo } from "../domain/EmpleadoEsActivo";

type MySQLEmpleado = {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    es_activo: boolean;
};

export class MySQLEmpleadoRepository implements EmpleadoRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(empleado: Empleado): Promise<Empleado> {
        const query = `
            INSERT INTO empleados (nombre, direccion, telefono, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            empleado.nombre.value,
            empleado.direccion.value,
            empleado.telefono.value,
            empleado.fechaCreacion.value,
            empleado.fechaModificacion.value,
            empleado.esActivo.value
        ]);

        const empleadoId = result.insertId;
        return this.getOneById(new EmpleadoId(empleadoId)) as Promise<Empleado>;
    }

    async getAll(): Promise<Empleado[]> {
        const query = 'SELECT * FROM empleados WHERE es_activo = true';

        const [rows] = await this.pool.query<(MySQLEmpleado & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Empleado(
                    new EmpleadoId(row.id),
                    new EmpleadoNombre(row.nombre),
                    new EmpleadoDireccion(row.direccion),
                    new EmpleadoTelefono(row.telefono),
                    new EmpleadoFechaCreacion(row.fecha_creacion),
                    new EmpleadoFechaModificacion(row.fecha_modificacion),
                    new EmpleadoEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(empleadoId: EmpleadoId): Promise<Empleado | null> {
        const query = 'SELECT * FROM empleados WHERE id = ?';

        const [rows] = await this.pool.query<(MySQLEmpleado & RowDataPacket)[]>(query, [empleadoId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Empleado(
            new EmpleadoId(row!.id),
            new EmpleadoNombre(row!.nombre),
            new EmpleadoDireccion(row!.direccion),
            new EmpleadoTelefono(row!.telefono),
            new EmpleadoFechaCreacion(row!.fecha_creacion),
            new EmpleadoFechaModificacion(row!.fecha_modificacion),
            new EmpleadoEsActivo(row!.es_activo)
        );
    }

    async update(empleado: Empleado): Promise<Empleado> {
        const query = `UPDATE empleados SET 
            nombre = ?,
            direccion = ?,
            telefono = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?
            WHERE id = ?`;

        await this.pool.query(query, [
            empleado.nombre.value,
            empleado.direccion.value,
            empleado.telefono.value,
            empleado.fechaCreacion.value,
            empleado.fechaModificacion.value,
            empleado.id.value
        ]);

        return this.getOneById(empleado.id) as Promise<Empleado>;
    }

    async delete(empleadoId: EmpleadoId): Promise<void> {
        const query = `UPDATE empleados SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [empleadoId.value]);
    }
}
