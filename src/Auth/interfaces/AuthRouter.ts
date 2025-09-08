import { Router } from "express";
import { AuthController } from "./AuthController";

export function authServiceRouter(authController: AuthController): Router {
    const router = Router();

    router.post('/login', authController.login.bind(authController));
    router.post('/register', authController.register.bind(authController));

    return router;
}