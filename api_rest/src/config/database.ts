import { createPool, type Pool } from "mysql2/promise";

export const dbPool: Pool = createPool({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'gestion_elcacho_db',
    connectionLimit: 10
});