import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "../application/DetalleCompraDTO";
import type { DetalleCompra } from "../domain/DetalleCompra";
import type { CompraId } from "../../Compra/domain/CompraId";
import type { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";

type MySQLDetalleCompra = {
    id_compra: number;
    codigo_producto: string;
    cantidad: number;
    precio_total: number;
    precio_unitario: number;
}

export class MySQLDetalleCompraRepository implements DetalleCompraRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(detalleCompra: DetalleCompra): Promise<DetalleCompraDTO> {
        const query = `
            INSERT INTO compras_detalles(id_compra, codigo_producto, cantidad, precio_total, precio_unitario)
            VALUES (?, ?, ?, ?, ?)
        `;

        await this.pool.query<ResultSetHeader>(query, [
            detalleCompra.compraId.value,
            detalleCompra.productoCodigoBarra.value,
            detalleCompra.cantidad.value,
            detalleCompra.precioTotal.value,
            detalleCompra.precioUnitario.value
        ]);

        return this.getOneById(detalleCompra.compraId, detalleCompra.productoCodigoBarra) as Promise<DetalleCompraDTO>;
    }

    async createMany(detalles: DetalleCompra[]): Promise<DetalleCompraDTO[]> {
        const connection = await this.pool.getConnection();
        try {
            const query = `
            INSERT INTO compras_detalles (id_compra, codigo_producto, cantidad, precio_total, precio_unitario)
            VALUES ?
        `;

            const values = detalles.map(d => [
                d.compraId.value,
                d.productoCodigoBarra.value,
                d.cantidad.value,
                d.precioTotal.value,
                d.precioUnitario.value,
            ]);

            await connection.beginTransaction();
            await connection.query(query, [values]);
            await connection.commit();
        } catch (err) {
            await connection.rollback();
            throw err;
        } finally {
            connection.release();
        }

        return this.getAllFromCompraById(detalles[0]!.compraId);
    }

    async getAll(): Promise<DetalleCompraDTO[]> {
        const query = `SELECT * FROM compras_detalles`;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLDetalleCompra)[]>(query);

        return rows.map(
            (row) => ({
                compraId: row!.id_compra,
                productoCodigoBarra: row!.codigo_producto,
                cantidad: row!.cantidad,
                precioTotal: row!.precio_total,
                precioUnitario: row!.precio_unitario
            })
        );
    }

    async getAllFromCompraById(compraId: CompraId): Promise<DetalleCompraDTO[]> {
        const query = `
            SELECT * FROM compras_detalles WHERE id_compra = ?
        `;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLDetalleCompra)[]>(query, [
            compraId.value,
        ]);

        return rows.map(
            (row) => ({
                compraId: row!.id_compra,
                productoCodigoBarra: row!.codigo_producto,
                cantidad: row!.cantidad,
                precioTotal: row!.precio_total,
                precioUnitario: row!.precio_unitario
            })
        );
    }

    async getOneById(compraId: CompraId, productoCodigoBarra: ProductoCodigoBarra): Promise<DetalleCompraDTO | null> {
        const query = `SELECT * FROM compras_detalles WHERE id_compra = ? AND codigo_producto = ?`;

        const [rows] = await this.pool.query<(MySQLDetalleCompra & RowDataPacket)[]>(query, [
            compraId.value,
            productoCodigoBarra.value
        ]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            compraId: row!.id_compra,
            productoCodigoBarra: row!.codigo_producto,
            cantidad: row!.cantidad,
            precioTotal: row!.precio_total,
            precioUnitario: row!.precio_unitario
        }
    }
}