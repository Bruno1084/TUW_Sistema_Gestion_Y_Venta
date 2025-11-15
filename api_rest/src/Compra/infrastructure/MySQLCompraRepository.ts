import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise"
import type { CompraRepository } from "../domain/CompraRepository"
import type { CompraDetailDTO, CompraDTO } from "../application/CompraDTO";
import type { DetalleCompra } from "../../DetalleCompra/domain/DetalleCompra";
import { Compra } from "../domain/Compra";
import { CompraId } from "../domain/CompraId";

type MySQLCompra = {
    id: number,
    id_proveedor: number,
    id_usuario: number,
    precio_total: number,
    fecha_creacion: Date
}

export class MySQLCompraRepository implements CompraRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(compra: Compra, detalles: DetalleCompra[]): Promise<CompraDetailDTO> {
        const connection = await this.pool.getConnection();
        let compraId = 0;
        try {

            const queryCompra = `
            INSERT INTO compras(id_proveedor, id_usuario, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
            `;

            const queryDetalle = `
            INSERT INTO compras_detalles (id_compra, codigo_producto, cantidad, precio_total, precio_unitario)
            VALUES ?
            `;

            await connection.beginTransaction();
            const [result] = await connection.query<ResultSetHeader>(queryCompra, [
                compra.proveedorId.value,
                compra.usuarioId.value,
                compra.precioTotal.value,
                compra.fechaCreacion.value
            ]);

            compraId = result.insertId;
            const values = detalles.map(d => [
                compraId,
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

        return this.getOneByIdWithDetail(new CompraId(compraId)) as Promise<CompraDetailDTO>;
    }

    async getAll(): Promise<CompraDTO[]> {
        const query = `
            SELECT
            c.id,
            c.precio_total,
            c.fecha_creacion,
            c.id_proveedor,
            c.id_usuario,
            p.nombre AS proveedor_nombre,
            u.nombre AS usuario_nombre
            FROM compras c
            JOIN proveedores p ON p.id = c.id_proveedor
            JOIN usuarios u ON u.id = c.id_usuario
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                precioTotal: row!.precio_total,
                fechaCreacion: row!.fecha_creacion,
                proveedor: {
                    id: row!.id_proveedor,
                    nombre: row!.proveedor_nombre
                },
                usuario: {
                    id: row!.id_usuario,
                    nombre: row!.usuario_nombre
                }
            })
        );
    }

    async getOneByIdWithDetail(compraId: CompraId): Promise<CompraDetailDTO | null> {
        const query = `
        SELECT
            c.id AS compra_id,
            c.precio_total AS compra_precio_total,
            c.fecha_creacion AS compra_fecha_creacion,
            
            p.id AS proveedor_id,
            p.nombre AS proveedor_nombre,
            
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

        FROM compras c
        JOIN proveedores p ON c.id_proveedor = p.id
        JOIN usuarios u ON c.id_usuario = u.id
        JOIN compras_detalles d ON c.id = d.id_compra
        JOIN productos pr ON d.codigo_producto = pr.codigo_barra
        JOIN rubros r ON r.id = pr.id_rubro
        JOIN marcas m ON m.id = pr.id_marca 
        WHERE c.id = ?
    `;

        const [rows] = await this.pool.query<RowDataPacket[]>(query, [compraId.value]);

        if (rows.length === 0) return null;

        const first = rows[0];
        const compra: CompraDetailDTO = {
            id: first!.compra_id,
            precioTotal: first!.compra_precio_total,
            fechaCreacion: first!.compra_fecha_creacion,
            proveedor: {
                id: first!.proveedor_id,
                nombre: first!.proveedor_nombre,
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

        return compra;
    }


}