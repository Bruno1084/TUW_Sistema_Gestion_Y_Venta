import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { VentaRepository } from "../domain/VentaRepository";
import type { VentaDetailDTO, VentaDTO } from "../application/VentaDTO";
import type { DetalleVenta } from "../../DetalleVenta/domain/DetalleVenta";
import { Venta } from "../domain/Venta";
import { VentaId } from "../domain/VentaId";
import type { DetalleCompra } from "../../DetalleCompra/domain/DetalleCompra";

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

    async create(venta: Venta, detalles: DetalleVenta[]): Promise<VentaDetailDTO> {
        const connection = await this.pool.getConnection();
        let ventaId = 0;
        try {
            const queryVenta = `
            INSERT INTO ventas(id_cliente, id_usuario, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
            `;

            const queryDetalle = `
            INSERT INTO ventas_detalles (id_venta, codigo_producto, cantidad, precio_total, precio_unitario)
            VALUES ?
            `;

            await connection.beginTransaction();
            const [result] = await connection.query<ResultSetHeader>(queryVenta, [
                venta.clienteId.value,
                venta.usuarioId.value,
                venta.precioTotal.value,
                venta.fechaCreacion.value
            ]);

            ventaId = result.insertId;
            const values = detalles.map(d => [
                ventaId,
                d.productoCodigoBarra.value,
                d.cantidad.value,
                d.precioTotal.value,
                d.precioUnitario.value,
            ]);

            await connection.query<ResultSetHeader>(queryDetalle, [values]);
            await connection.commit();
        } catch (err: any) {
            await connection.rollback();
            throw err;
        } finally {
            connection.release();
        }

        return this.getOneByIdWithDetail(new VentaId(ventaId)) as Promise<VentaDetailDTO>;
    }

    async getAll(): Promise<VentaDTO[]> {
        const query = `
            SELECT
            v.id,
            v.precio_total,
            v.fecha_creacion,
            v.id_cliente,
            v.id_usuario,
            c.nombre AS cliente_nombre,
            u.nombre AS usuario_nombre
            FROM ventas v
            JOIN clientes c ON c.id = v.id_cliente
            JOIN usuarios u ON u.id = v.id_usuario
        `;

        const [rows] = await this.pool.query<(MySQLVenta & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion,
                cliente: {
                    id: row!.id_cliente,
                    nombre: row!.cliente_nombre
                },
                usuario: {
                    id: row!.id_usuario,
                    nombre: row!.usuario_nombre
                }
            })
        );
    }

    async getOneByIdWithDetail(ventaId: VentaId): Promise<VentaDetailDTO | null> {
        const query = `
        SELECT
            v.id AS venta_id,
            v.precio_total AS venta_precio_total,
            v.fecha_creacion AS venta_fecha_creacion,
            
            c.id AS cliente_id,
            c.nombre AS cliente_nombre,
            
            u.id AS usuario_id,
            u.nombre AS usuario_nombre,

            d.cantidad AS detalle_cantidad,
            d.precio_unitario AS detalle_precio_unitario,
            d.precio_total AS detalle_precio_total,

            pr.codigo_barra AS producto_codigo_barra,
            pr.descripcion AS producto_descripcion,
            pr.precio_compra AS producto_precio_compra,
            pr.precio_venta AS producto_precio_venta,
            pr.stock AS producto_stock,
            pr.img_uri AS producto_img_uri,

            r.id AS rubro_id,
            r.nombre AS rubro_nombre,
            r.fecha_creacion AS rubro_fecha_creacion,
            r.fecha_modificacion AS rubro_fecha_modificacion,

            m.id AS marca_id,
            m.nombre AS marca_nombre,
            m.fecha_creacion AS marca_fecha_creacion,
            m.fecha_modificacion AS marca_fecha_modificacion

        FROM ventas v
        JOIN clientes c ON v.id_cliente = c.id
        JOIN usuarios u ON v.id_usuario = u.id
        JOIN ventas_detalles d ON v.id = d.id_venta
        JOIN productos pr ON d.codigo_producto = pr.codigo_barra
        JOIN rubros r ON r.id = pr.id_rubro
        JOIN marcas m ON m.id = pr.id_marca 
        WHERE v.id = ?
    `;

        const [rows] = await this.pool.query<RowDataPacket[]>(query, [ventaId.value]);

        if (rows.length === 0) return null;

        const first = rows[0];
        const venta: VentaDetailDTO = {
            id: first!.venta_id,
            precioTotal: first!.venta_precio_total,
            fechaCreacion: first!.venta_fecha_creacion,
            cliente: {
                id: first!.cliente_id,
                nombre: first!.cliente_nombre,
            },
            usuario: {
                id: first!.usuario_id,
                nombre: first!.usuario_nombre,
            },
            detalles: rows.map((r) => ({
                cantidad: r.detalle_cantidad,
                precioUnitario: r.detalle_precio_unitario,
                precioTotal: r.detalle_precio_total,
                producto: {
                    codigoBarra: r.producto_codigo_barra,
                    descripcion: r.producto_descripcion,
                    precioCompra: r.producto_precio_compra,
                    precioVenta: r.producto_precio_venta,
                    stock: r.producto_stock,
                    rubro: {
                        id: r.rubro_id,
                        nombre: r.rubro_nombre,
                        fechaCreacion: r.rubro_fecha_creacion,
                        fechaModificacion: r.rubro_fecha_modificacion
                    },
                    marca: {
                        id: r.marca_id,
                        nombre: r.marca_nombre,
                        fechaCreacion: r.fecha_creacion,
                        fechaModificacion: r.fecha_modificacion
                    },
                    imgUri: r.producto_img_uri,
                },
            })),
        };

        return venta;
    }
}