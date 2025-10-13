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

    async create(compra: Compra): Promise<CompraDetailDTO> {
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
        return this.getOneByIdWithDetail(new CompraId(compraId)) as Promise<CompraDetailDTO>;
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
            p.id AS proveedor_id,
            p.nombre AS proveedor_nombre,
            p.direccion AS proveedor_direccion,
            p.telefono AS proveedor_telefono,
            p.fecha_creacion AS proveedor_fecha_creacion,
            p.fecha_modificacion AS proveedor_fecha_modificacion,
            e.id AS empleado_id,
            e.nombre AS empleado_nombre,
            e.direccion AS empleado_direccion,
            e.telefono AS empleado_telefono,
            e.fecha_creacion AS empleado_fecha_creacion,
            e.fecha_modificacion AS empleado_fecha_modificacion
            FROM compras c
            JOIN proveedores p ON c.id_proveedor = p.id
            JOIN empleados e ON c.id_empleado = e.id
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion,
                proveedor: {
                    id: row!.proveedor_id,
                    nombre: row!.proveedor_nombre,
                    direccion: row!.proveedor_direccion,
                    telefono: row!.proveedor_telefono,
                    fechaCreacion: row!.proveedor_fecha_creacion,
                    fechaModificacion: row!.proveedor_fecha_modificacion
                },
                empleado: {
                    id: row!.empleado_id,
                    nombre: row!.empleado_nombre,
                    direccion: row!.empleado_direccion,
                    telefono: row!.empleado_telefono,
                    fechaCreacion: row!.empleado_fecha_creacion,
                    fechaModificacion: row!.empleado_fecha_modificacion
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

    async getOneByIdWithDetail(compraId: CompraId): Promise<CompraDetailDTO | null> {
        const query = `
            SELECT
            c.id,
            c.precio_total,
            c.fecha_creacion,
            p.id AS proveedor_id,
            p.nombre AS proveedor_nombre,
            p.direccion AS proveedor_direccion,
            p.telefono AS proveedor_telefono,
            p.fecha_creacion AS proveedor_fecha_creacion,
            p.fecha_modificacion AS proveedor_fecha_modificacion,
            e.id AS empleado_id,
            e.nombre AS empleado_nombre,
            e.direccion AS empleado_direccion,
            e.telefono AS empleado_telefono,
            e.fecha_creacion AS empleado_fecha_creacion,
            e.fecha_modificacion AS empleado_fecha_modificacion
            FROM compras c
            JOIN proveedores p ON c.id_proveedor = p.id
            JOIN empleados e ON c.id_empleado = e.id
            WHERE c.id = ?
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query, [compraId.value]);

        if (rows.length === 0) return null;

        const row = rows[0];
        return {
            id: row!.id,
            precioTotal: row!.precio_total,
            fechaCreacion: row!.fecha_creacion,
            proveedor: {
                id: row!.proveedor_id,
                nombre: row!.proveedor_nombre,
                direccion: row!.proveedor_direccion,
                telefono: row!.proveedor_telefono,
                fechaCreacion: row!.proveedor_fecha_creacion,
                fechaModificacion: row!.proveedor_fecha_modificacion
            },
            empleado: {
                id: row!.empleado_id,
                nombre: row!.empleado_nombre,
                direccion: row!.empleado_direccion,
                telefono: row!.empleado_telefono,
                fechaCreacion: row!.empleado_fecha_creacion,
                fechaModificacion: row!.empleado_fecha_modificacion
            }
        };
    }
}