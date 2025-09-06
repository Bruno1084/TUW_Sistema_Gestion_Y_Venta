import type { Pool, RowDataPacket } from "mysql2/promise";
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
    esActivo: boolean;
};

export class MySQLEmpleadoRepository implements EmpleadoRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(empleado: Empleado): Promise<void> {
        const query = `
      INSERT INTO empleados (id, nombre, direccion, telefono)
      VALUES (?, ?, ?, ?)
    `;

        await this.pool.query(query, [
            empleado.id.value,
            empleado.nombre.value,
            empleado.direccion.value,
            empleado.telefono.value
        ]);
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
                    new EmpleadoEsActivo(row.esActivo)
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
            new EmpleadoEsActivo(row!.esActivo)
        );
    }

    async update(empleado: Empleado): Promise<void> {
        const query = `UPDATE empleados SET 
            nombre = ?,
            direccion = ?,
            telefono = ? 
            WHERE id = ?`;

        await this.pool.query(query, [
            empleado.nombre.value,
            empleado.direccion.value,
            empleado.telefono.value,
            empleado.id.value
        ]);
    }

    async delete(empleadoId: EmpleadoId): Promise<void> {
        const query = 'DELETE FROM empleados WHERE id = ?';

        await this.pool.query(query, [empleadoId.value]);
    }
}
