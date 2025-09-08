import type { CajeroRepository } from "../domain/CajeroRepository";
import { EmpleadoDireccion } from "../../Empleado/domain/EmpleadoDireccion";
import { EmpleadoEsActivo } from "../../Empleado/domain/EmpleadoEsActivo";
import { EmpleadoFechaCreacion } from "../../Empleado/domain/EmpleadoFechaCreacion";
import { EmpleadoFechaModificacion } from "../../Empleado/domain/EmpleadoFechaModificacion";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import { EmpleadoTelefono } from "../../Empleado/domain/EmpleadoTelefono";
import { Cajero } from "../domain/Cajero";
import { CajeroContrasenia } from "../domain/CajeroContrasenia";

export class CajeroCreate {
    constructor(private repository: CajeroRepository) { }

    async run(
        nombre: string,
        direccion: string,
        telefono: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
        contrasenia: string
    ): Promise<void> {
        const cajero = new Cajero(
            new EmpleadoId(0),
            new EmpleadoNombre(nombre),
            new EmpleadoDireccion(direccion),
            new EmpleadoTelefono(telefono),
            new EmpleadoFechaCreacion(fechaCreacion),
            new EmpleadoFechaModificacion(fechaModificacion),
            new EmpleadoEsActivo(true),
            new CajeroContrasenia(contrasenia)
        );

        return this.repository.create(cajero);
    }
}