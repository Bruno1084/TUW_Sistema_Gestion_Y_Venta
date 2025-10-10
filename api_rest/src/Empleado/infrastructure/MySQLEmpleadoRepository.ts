import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { EmpleadoRepository } from "../domain/EmpleadoRepository";
import type { EmpleadoDTO } from "../application/EmpleadoDTO";
import { Empleado } from "../domain/Empleado";
import { EmpleadoId } from "../domain/EmpleadoId";

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

    async create(empleado: Empleado): Promise<EmpleadoDTO> {
        const query = `
            INSERT INTO empleados (nombre, direccion, telefono, fecha_creacion, fecha_modificacion)
            VALUES (?, ?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            empleado.nombre.value,
            empleado.direccion.value,
            empleado.telefono.value,
            empleado.fechaCreacion.value,
            empleado.fechaModificacion.value,
        ]);

        const empleadoId = result.insertId;
        return this.getOneById(new EmpleadoId(empleadoId)) as Promise<EmpleadoDTO>;
    }

    async getAll(): Promise<EmpleadoDTO[]> {
        const query = `
        SELECT
        id, nombre, direccion, telefono, fecha_creacion, fecha_modificacion
        FROM empleados WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLEmpleado & RowDataPacket)[]>(query);

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

    async getOneById(empleadoId: EmpleadoId): Promise<EmpleadoDTO | null> {
        const query = `
        SELECT
        id, nombre, direccion, telefono, fecha_creacion, fecha_modificacion
        FROM empleados WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLEmpleado & RowDataPacket)[]>(query, [empleadoId.value]);

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

    async update(empleado: Empleado): Promise<EmpleadoDTO> {
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

        return this.getOneById(empleado.id) as Promise<EmpleadoDTO>;
    }

    async delete(empleadoId: EmpleadoId): Promise<void> {
        const query = `UPDATE empleados SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [empleadoId.value]);
    }
}
