import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { DetalleVentaRepository } from "../domain/DetalleVentaRepository";
import type { DetalleVenta } from "../domain/DetalleVenta";
import type { DetalleVentaDTO } from "../application/DetalleVentaDTO";
import type { VentaId } from "../../Venta/domain/VentaId";
import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";

type MySQLDetalleVenta = {
    id_venta: number;
    codigo_producto: string;
    cantidad: number;
    precio_total: number;
    precio_unitario: number;
}

export class MySQLDetalleVentaRepository implements DetalleVentaRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(detalleVenta: DetalleVenta): Promise<DetalleVentaDTO> {
        const query = `
            INSERT INTO ventas_detalles(id_venta, codigo_producto, cantidad, precio_total, precio_unitario)
            VALUES (?, ?, ?, ?, ?)
        `;

        await this.pool.query<ResultSetHeader>(query, [
            detalleVenta.ventaId.value,
            detalleVenta.productoCodigoBarra.value,
            detalleVenta.cantidad.value,
            detalleVenta.precioTotal.value,
            detalleVenta.precioUnitario.value
        ]);

        return this.getOneById(detalleVenta.ventaId, detalleVenta.productoCodigoBarra) as Promise<DetalleVentaDTO>;
    }

    async getAll(): Promise<DetalleVentaDTO[]> {
        const query = `SELECT * FROM ventas_detalles`;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLDetalleVenta)[]>(query);

        return rows.map(
            (row) => ({
                ventaId: row!.id_compra,
                productoCodigoBarra: row!.codigo_producto,
                cantidad: row!.cantidad,
                precioTotal: row!.precio_total,
                precioUnitario: row!.precio_unitario
            })
        );
    }


    async getAllFromVentaById(ventaId: VentaId): Promise<DetalleVentaDTO[]> {
        const query = `
            SELECT * FROM ventas_detalles WHERE id_venta = ?
        `;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLDetalleVenta)[]>(query, [
            ventaId.value,
        ]);

        return rows.map(
            (row) => ({
                ventaId: row!.id_compra,
                productoCodigoBarra: row!.codigo_producto,
                cantidad: row!.cantidad,
                precioTotal: row!.precio_total,
                precioUnitario: row!.precio_unitario
            })
        );
    }

    async getOneById(ventaId: VentaId, productoCodigoBarra: ProductoCodigoBarra): Promise<DetalleVentaDTO | null> {
        const query = `SELECT * FROM ventas_detalles WHERE id_venta = ? AND codigo_producto = ?`;

        const [rows] = await this.pool.query<(MySQLDetalleVenta & RowDataPacket)[]>(query, [
            ventaId.value,
            productoCodigoBarra.value
        ]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            ventaId: row!.id_compra,
            productoCodigoBarra: row!.codigo_producto,
            cantidad: row!.cantidad,
            precioTotal: row!.precio_total,
            precioUnitario: row!.precio_unitario
        }
    }
}