import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise"
import type { MarcaRepository } from "../domain/MarcaRepository"
import type { MarcaDTO } from "../application/MarcaDTO";
import { Marca } from "../domain/Marca";
import { MarcaId } from "../domain/MarcaId";

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

    async create(marca: Marca): Promise<MarcaDTO> {
        const query = `
            INSERT INTO marcas(nombre, fecha_creacion, fecha_modificacion)
            VALUES (?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            marca.nombre.value,
            marca.fechaCreacion.value,
            marca.fechaModificacion.value,
        ]);

        const marcaId = result.insertId;
        return this.getOneById(new MarcaId(marcaId)) as Promise<MarcaDTO>;
    }

    async getAll(): Promise<MarcaDTO[]> {
        const query = `
        SELECT
        id, nombre, fecha_creacion, fecha_modificacion
        FROM marcas WHERE es_activo = true
        `;

        const [rows] = await this.pool.query<(MySQLMarca & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                nombre: row!.nombre,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
            })
        );
    }

    async getOneById(marcaId: MarcaId): Promise<MarcaDTO | null> {
        const query = `
        SELECT
        id, nombre, fecha_creacion, fecha_modificacion
        FROM marcas WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLMarca & RowDataPacket)[]>(query, [marcaId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            id: row!.id,
            nombre: row!.nombre,
            fechaCreacion: row!.fecha_creacion,
            fechaModificacion: row!.fecha_modificacion,
        }
    }

    async update(marca: Marca): Promise<MarcaDTO> {
        const query = `
            UPDATE marcas SET
            nombre = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?
            WHERE id = ?
        `;

        await this.pool.query(query, [
            marca.nombre.value,
            marca.fechaCreacion.value,
            marca.fechaModificacion.value,
            marca.id.value
        ]);

        return this.getOneById(marca.id) as Promise<MarcaDTO>;
    }

    async delete(marcaId: MarcaId): Promise<void> {
        const query = `UPDATE marcas SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [marcaId.value]);
    }
}