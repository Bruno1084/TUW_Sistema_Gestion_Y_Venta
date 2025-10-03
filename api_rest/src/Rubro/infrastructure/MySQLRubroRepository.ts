import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { RubroRepository } from "../domain/RubroRepository";
import type { RubroDTO } from "../application/RubroDTO";
import { RubroId } from "../domain/RubroId";
import { Rubro } from "../domain/Rubro";

type MySQLRubro = {
    id: number;
    nombre: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    es_activo: boolean;
}

export class MySQLRubroRepository implements RubroRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(rubro: Rubro): Promise<RubroDTO> {
        const query = `
            INSERT INTO rubros(nombre, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?)
        `;

        const [result] = await this.pool.query<ResultSetHeader>(query, [
            rubro.nombre.value,
            rubro.fechaCreación.value,
            rubro.fechaModificacion.value,
            rubro.esActivo.value
        ]);

        const rubroId = result.insertId;
        return this.getOneById(new RubroId(rubroId)) as Promise<RubroDTO>;
    }

    async getAll(): Promise<RubroDTO[]> {
        const query = `SELECT * FROM rubros WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLRubro & RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                id: row!.id,
                nombre: row!.nombre,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
                esActivo: row!.es_activo
            })
        );
    }

    async getOneById(rubroId: RubroId): Promise<RubroDTO | null> {
        const query = `SELECT * FROM rubros WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLRubro & RowDataPacket)[]>(query, [rubroId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            id: row!.id,
            nombre: row!.nombre,
            fechaCreacion: row!.fecha_creacion,
            fechaModificacion: row!.fecha_modificacion,
            esActivo: row!.es_activo
        }
    }

    async update(rubro: Rubro): Promise<RubroDTO> {
        const query = `
            UPDATE rubros SET
            nombre = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?
            WHERE id = ?
        `;

        await this.pool.query(query, [
            rubro.nombre.value,
            rubro.fechaCreación.value,
            rubro.fechaModificacion.value,
            rubro.id.value
        ]);

        return this.getOneById(rubro.id) as Promise<RubroDTO>;
    }

    async delete(rubroId: RubroId): Promise<void> {
        const query = `UPDATE rubros SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [rubroId]);
    }
}