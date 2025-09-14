import type { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import type { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import type { Cajero } from "./Cajero";

export interface CajeroRepository {
    create(cajero: Cajero): Promise<Cajero>;
    getOneById(empladoId: EmpleadoId): Promise<Cajero | null>;
    getOneByNombre(nombre: EmpleadoNombre): Promise<Cajero | null>;
}