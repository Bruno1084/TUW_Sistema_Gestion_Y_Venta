import type { Request, Response, NextFunction } from "express";
import jwt from "jsonwebtoken";

export function authMiddleware(req: Request, res: Response, next: NextFunction) {
    const publicRoutes = [
        "/api/ping",
        "/api/auth/login",
        "/api/auth/register",
    ];

    if (publicRoutes.includes(req.path)) {
        return next();
    }

    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];

    if (!token) {
        return res.status(401).json({ error: "Token requerido" });
    }

    try {
        const payload = jwt.verify(token, process.env.JWT_SECRET || "super_secret");
        (req as any).user = payload;
        next();
    } catch (err) {
        return res.status(403).json({ error: "Token inválido o expirado" });
    }
}
