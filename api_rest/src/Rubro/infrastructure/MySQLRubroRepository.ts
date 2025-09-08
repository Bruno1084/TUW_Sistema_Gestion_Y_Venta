import type { Pool, RowDataPacket } from "mysql2/promise";
import type { RubroRepository } from "../domain/RubroRepository";
import { RubroId } from "../domain/RubroId";
import { Rubro } from "../domain/Rubro";
import { RubroNombre } from "../domain/RubroNombre";
import { RubroFechaCracion } from "../domain/RubroFechaCreacion";
import { RubroFechaModificacion } from "../domain/RubroFechaModificacion";
import { RubroEsActivo } from "../domain/RubroEsActivo";

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

    async create(rubro: Rubro): Promise<void> {
        const query = `
            INSERT INTO rubros(nombre, fecha_creacion, fecha_modificacion, es_activo)
            VALUES (?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            rubro.nombre.value,
            rubro.fechaCreación.value,
            rubro.fechaModificacion.value,
            rubro.esActivo.value
        ]);
    }

    async getAll(): Promise<Rubro[]> {
        const query = `SELECT * FROM rubros WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLRubro & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Rubro(
                    new RubroId(row.id),
                    new RubroNombre(row.nombre),
                    new RubroFechaCracion(row.fecha_creacion),
                    new RubroFechaModificacion(row.fecha_modificacion),
                    new RubroEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(rubroId: RubroId): Promise<Rubro | null> {
        const query = `SELECT * FROM rubros WHERE id = ?`;

        const [rows] = await this.pool.query<(MySQLRubro & RowDataPacket)[]>(query, [rubroId.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Rubro(
            new RubroId(row!.id),
            new RubroNombre(row!.nombre),
            new RubroFechaCracion(row!.fecha_creacion),
            new RubroFechaModificacion(row!.fecha_modificacion),
            new RubroEsActivo(row!.es_activo)
        );
    }

    async update(rubro: Rubro): Promise<void> {
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
    }

    async delete(rubroId: RubroId): Promise<void> {
        const query = `UPDATE rubros SET es_activo = false WHERE id = ?`;

        await this.pool.query(query, [rubroId]);
    }
}