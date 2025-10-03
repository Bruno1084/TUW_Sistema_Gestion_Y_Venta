import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise"
import type { CompraRepository } from "../domain/CompraRepository"
import type { CompraDetailDTO, CompraSimpleDTO } from "../application/CompraDTO";
import { Compra } from "../domain/Compra";
import { CompraId } from "../domain/CompraId";

type MySQLCompra = {
    id: number,
    id_proveedor: number,
    id_empleado: number,
    precio_total: number,
    fecha_creacion: Date
}

export class MySQLCompraRepository implements CompraRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(compra: Compra): Promise<CompraSimpleDTO> {
        const query = `
            INSERT INTO compras(id_proveedor, id_empleado, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            compra.proveedorId.value,
            compra.empleadoId.value,
            compra.precioTotal.value,
            compra.fechaCreacion.value
        ]);

        const compraId = result.insertId;
        return this.getOneById(new CompraId(compraId)) as Promise<CompraSimpleDTO>;
    }

    async getAll(): Promise<CompraSimpleDTO[]> {
        const query = `
            SELECT * FROM compras
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion,
                proveedorId: row!.id_proveedor,
                empleadoId: row!.id_empleado
            })
        );
    }

    async getAllWithDetail(): Promise<CompraDetailDTO[]> {
        const query = `
            SELECT
            c.id,
            c.precio_total,
            c.fecha_creacion,
            c.fecha_modificacion,
            p.id as proveedor_id, p.nombre as proveedor_nombre,
            e.id as empleado_id, e.nombre as empleado_nombre
            FROM compras c
            JOIN proveedores p ON c.id_proveedor = p.id
            JOIN empleados e ON c.id_empleado = e.id
            WHERE c.es_activo = true
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion,
                proveedor: {
                    id: row!.proveedor_id,
                    nombre: row!.proveedor_nombre
                },
                empleado: {
                    id: row!.empleado_id,
                    nombre: row!.proveedor_nombre
                }
            })
        )
    }

    async getOneById(compraId: CompraId): Promise<CompraSimpleDTO | null> {
        const query = `SELECT * FROM compras WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query, [compraId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            id: row!.id,
            precioTotal: row!.precio_total,
            fechaCreacion: row!.fecha_creacion,
            proveedorId: row!.id_proveedor,
            empleadoId: row!.id_empleado
        }
    }
}