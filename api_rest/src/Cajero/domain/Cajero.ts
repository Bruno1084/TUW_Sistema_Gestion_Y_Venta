import type { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import type { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import type { EmpleadoDireccion } from "../../Empleado/domain/EmpleadoDireccion";
import type { EmpleadoTelefono } from "../../Empleado/domain/EmpleadoTelefono";
import type { EmpleadoFechaCreacion } from "../../Empleado/domain/EmpleadoFechaCreacion";
import type { EmpleadoFechaModificacion } from "../../Empleado/domain/EmpleadoFechaModificacion";
import type { EmpleadoEsActivo } from "../../Empleado/domain/EmpleadoEsActivo";
import { CajeroContrasenia } from "./CajeroContrasenia";
import { Empleado } from "../../Empleado/domain/Empleado";

export class Cajero extends Empleado {
    contrasenia: CajeroContrasenia;

    constructor(
        id: EmpleadoId,
        nombre: EmpleadoNombre,
        direccion: EmpleadoDireccion,
        telefono: EmpleadoTelefono,
        fechaCreacion: EmpleadoFechaCreacion,
        fechaModificacion: EmpleadoFechaModificacion,
        esActivo: EmpleadoEsActivo,
        contrasenia: CajeroContrasenia
    ) {
        super(id, nombre, direccion, telefono, fechaCreacion, fechaModificacion, esActivo);

        this.contrasenia = contrasenia;
    }
}