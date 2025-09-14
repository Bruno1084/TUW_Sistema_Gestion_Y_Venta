import type { Pool, RowDataPacket } from "mysql2/promise"
import type { CompraRepository } from "../domain/CompraRepository"
import { Compra } from "../domain/Compra";
import { CompraId } from "../domain/CompraId";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { CompraPrecioTotal } from "../domain/CompraPrecioTotal";
import { CompraFechaCreacion } from "../domain/CompraFechaCreacion";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";

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

    async create(compra: Compra): Promise<void> {
        const query = `
            INSERT INTO compras(id_proveedor, id_empleado, precio_total, fecha_creacion)
            VALUES (?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            compra.proveedorId.value,
            compra.empleadoId.value,
            compra.precioTotal.value,
            compra.fechaCreacion.value
        ]);
    }

    async getAll(): Promise<Compra[]> {
        const query = `
            SELECT * FROM compras
        `;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Compra(
                    new CompraId(row.id),
                    new CompraPrecioTotal(row.precio_total),
                    new CompraFechaCreacion(row.fecha_creacion),
                    new ProveedorId(row.id_proveedor),
                    new EmpleadoId(row.id_empleado)
                )
        );
    }

    async getOneById(compraId: CompraId): Promise<Compra | null> {
        const query = `SELECT * FROM compras WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLCompra & RowDataPacket)[]>(query, [compraId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Compra(
            new CompraId(row!.id),
            new CompraPrecioTotal(row!.precio_total),
            new CompraFechaCreacion(row!.fecha_creacion),
            new ProveedorId(row!.id_proveedor),
            new EmpleadoId(row!.id_empleado)
        );
    }
}