import type { Pool } from "mysql2/promise";
import { MySQLCajeroRepository } from "../Cajero/infrastructure/MySQLCajeroRepository";
import { AuthService } from "./application/AuthService";
import { AuthController } from "./interfaces/AuthController";
import { authServiceRouter } from "./interfaces/AuthRouter";

export function initAuthModule(pool: Pool) {
    const cajeroRepo = new MySQLCajeroRepository(pool);
    const authService = new AuthService(cajeroRepo);
    const authController = new AuthController(authService);

    return authServiceRouter(authController);
}
