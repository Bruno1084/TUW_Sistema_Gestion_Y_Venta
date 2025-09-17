import { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import type { CajeroRepository } from "../domain/CajeroRepository";
import jwt from "jsonwebtoken";

export class CajeroLogin {
    constructor(private repository: CajeroRepository) { }

    async run(nombre: string, contrasenia: string): Promise<string> {
        const cajero = await this.repository.getOneByNombre(new EmpleadoNombre(nombre));

        if (!cajero) throw new Error("Cajero no encontrado");
        if (!cajero.contrasenia.comparar(contrasenia)) throw new Error("Contraseña inválida");

        const payload = { sub: cajero.id.value, role: "cajero" };
        return jwt.sign(payload, "default_secret", { expiresIn: "1h" });
    }
}