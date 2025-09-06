import mysql from 'mysql2/promise';

export function createPoolMySQL() {
    const pool = mysql.createPool({
        host: 'localhost',
        user: 'root',
        database: '',
        connectionLimit: 10,
        idleTimeout: 60000,
        queueLimit: 0,
        enableKeepAlive: true,
        keepAliveInitialDelay: 0,
    });

    return pool
}