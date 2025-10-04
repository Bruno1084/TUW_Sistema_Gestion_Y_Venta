import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDetailDTO, VentaSimpleDTO } from "../application/VentaDTO";
import { Venta } from "../domain/Venta";
import { VentaId } from "../domain/VentaId";

type MySQLVenta = {
    id: number;
    id_cliente: number;
    id_empleado: number;
    precio_total: number;
    fecha_creacion: Date;
}

export class MySQLVentaRepository implements VentaRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(venta: Venta): Promise<VentaSimpleDTO> {
        const query = `
            INSERT INTO ventas(id_cliente, id_empleado, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            venta.clienteId.value,
            venta.empleadoId.value,
            venta.precioTotal.value,
            venta.fechaCreacion.value
        ]);

        const ventaId = result.insertId;
        return this.getOneById(new VentaId(ventaId)) as Promise<VentaSimpleDTO>;
    }

    async getAll(): Promise<VentaSimpleDTO[]> {
        const query = `
            SELECT * FROM ventas
        `;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLVenta)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                clienteId: row!.id_cliente,
                empleadoId: row!.id_empleado,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion
            })
        );
    }

    async getAllWithDetail(): Promise<VentaDetailDTO[]> {
        const query = `
            SELECT
            v.id,
            v.id_cliente,
            v.id_empleado,
            v.precio_total,
            v.fecha_creacion
            c.id as cliente_id, c.nombre,
            e.id as empleado_id, e.nombre
            FROM ventas v
            JOIN clientes c ON v.id_cliente = c.id,
            JOIN empleado e ON v.id_empleado = e.id
        `;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLVenta)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                cliente: {
                    id: row!.id_cliente,
                    nombre: row!.cliente_nombre
                },
                empleado: {
                    id: row!.id_empleado,
                    nombre: row!.empleado_nombre
                },           
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion
            })
        )
    }

    async getOneById(ventaId: VentaId): Promise<VentaSimpleDTO | null> {
        const query = `SELECT * FROM ventas WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLVenta & RowDataPacket)[]>(query, [ventaId.value]);

        if(rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            id: row!.id,
            clienteId: row!.id_cliente,
            empleadoId: row!.id_empleado,
            precioTotal: row!.precio_total,
            fechaCreacion: row!.fecha_creacion
        }
    }
}