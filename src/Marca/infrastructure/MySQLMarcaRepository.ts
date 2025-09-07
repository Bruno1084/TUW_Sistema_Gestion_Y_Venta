import type { Pool, RowDataPacket } from "mysql2/promise"
import type { MarcaRepository } from "../domain/MarcaRepository"
import { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";
import { MarcaNombre } from "../domain/MarcaNombre";
import { MarcaFechaCreacion } from "../domain/MarcaFechaCreacion";
import { MarcaFechaModificacion } from "../domain/MarcaFechaModificacion";
import { MarcaEsActivo } from "../domain/MarcaEsActivo";

type MySQLMarca = {
    id: number,
    nombre: string,
    fecha_creacion: Date,
    fecha_modificacion: Date
    es_activo: boolean
}

export class MySQLMarcaRepository implements MarcaRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(marca: Marca): Promise<void> {
        const query = `
            INSERT INTO marcas(nombre, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            marca.nombre.value,
            marca.fechaCreacion.value,
            marca.fechaModificacion.value,
            marca.esActivo.value
        ]);
    }

    async getAll(): Promise<Marca[]> {
        const query = `SELECT * FROM marcas WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLMarca & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Marca(
                    new MarcaId(row.id),
                    new MarcaNombre(row.nombre),
                    new MarcaFechaCreacion(row.fecha_creacion),
                    new MarcaFechaModificacion(row.fecha_modificacion),
                    new MarcaEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(marcaId: MarcaId): Promise<Marca | null> {
        const query = `SELECT * FROM marcas WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLMarca & RowDataPacket)[]>(query, [marcaId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Marca(
            new MarcaId(row!.id),
            new MarcaNombre(row!.nombre),
            new MarcaFechaCreacion(row!.fecha_creacion),
            new MarcaFechaModificacion(row!.fecha_modificacion),
            new MarcaEsActivo(row!.es_activo)
        );
    }

    async update(marca: Marca): Promise<void> {
        const query = `
            UPDATES marcas SET
            nombre = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            WHERE id = ?
        `;

        await this.pool.query(query, [
            marca.nombre.value,
            marca.fechaCreacion.value,
            marca.fechaModificacion.value,
            marca.esActivo.value
        ]);
    }

    async delete(marcaId: MarcaId): Promise<void> {
        const query = `UPDATE marcas SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [marcaId.value]);
    }
}