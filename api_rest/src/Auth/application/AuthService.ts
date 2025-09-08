import jwt from "jsonwebtoken";
import type { CajeroRepository } from "../../Cajero/domain/CajeroRepository";
import { CajeroContrasenia } from "../../Cajero/domain/CajeroContrasenia";
import { Empleado } from "../../Empleado/domain/Empleado";
import { Cajero } from "../../Cajero/domain/Cajero";
import { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";

const JWT_SECRET = process.env.JWT_SECRET || "super_secret";

export class AuthService {
  constructor(private cajeroRepo: CajeroRepository) { }

  async register(data: {
    empleado: Empleado;
    contraseniaPlano: string;
  }): Promise<void> {
    const contrasenia = new CajeroContrasenia(data.contraseniaPlano);

    await this.cajeroRepo.create(new Cajero(
      data.empleado.id,
      data.empleado.nombre,
      data.empleado.direccion,
      data.empleado.telefono,
      data.empleado.fechaCreacion,
      data.empleado.fechaModificacion,
      data.empleado.esActivo,
      contrasenia
    ));
  }

  async login(nombre: string, contraseniaPlano: string): Promise<string> {
    const cajero = await this.cajeroRepo.getOneByNombre(new EmpleadoNombre(nombre));
    if (!cajero) throw new Error("Cajero no encontrado");

    if (!cajero.contrasenia.comparar(contraseniaPlano)) {
      throw new Error("Contraseña incorrecta");
    }

    const payload = { sub: cajero.id.value, role: "cajero" };
    return jwt.sign(payload, JWT_SECRET, { expiresIn: "1h" });
  }
}
