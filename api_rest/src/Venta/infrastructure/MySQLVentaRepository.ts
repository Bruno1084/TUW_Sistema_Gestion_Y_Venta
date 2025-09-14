import type { Pool, RowDataPacket } from "mysql2/promise";
import type { VentaRepository } from "../domain/VentaRepository";
import { Venta } from "../domain/Venta";
import { VentaId } from "../domain/VentaId";
import { ClienteId } from "../../Cliente/domain/ClienteId";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import { VentaPrecioTotal } from "../domain/VentaPrecioTotal";
import { VentaFechaCreacion } from "../domain/VentaFechaCreacion";

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

    async create(venta: Venta): Promise<void> {
        const query = `
            INSERT INTO ventas(id_cliente, id_empleado, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            venta.clienteId.value,
            venta.empleadoId.value,
            venta.precioTotal.value,
            venta.fechaCreacion.value
        ]);
    }

    async getAll(): Promise<Venta[]> {
        const query = `
            SELECT * FROM ventas
        `;

        const [rows] = await this.pool.query<(MySQLVenta & RowDataPacket)[]>(query);

        return rows.map(
            (row) => 
                new Venta(
                    new VentaId(row.id),
                    new ClienteId(row.id_cliente),
                    new EmpleadoId(row.id_empleado),
                    new VentaPrecioTotal(row.precio_total),
                    new VentaFechaCreacion(row.fecha_creacion)
                )
        )
    }

    async getOneById(ventaId: VentaId): Promise<Venta | null> {
        const query = `SELECT * FROM ventas WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLVenta & RowDataPacket)[]>(query, [ventaId.value]);

        if(rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Venta(
            new VentaId(row!.id),
            new ClienteId(row!.id_cliente),
            new EmpleadoId(row!.id_empleado),
            new VentaPrecioTotal(row!.precio_total),
            new VentaFechaCreacion(row!.fecha_creacion)
        );
    }
}