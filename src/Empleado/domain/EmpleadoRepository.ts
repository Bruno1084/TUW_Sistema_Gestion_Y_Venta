import { Empleado } from "./Empleado";
import { EmpleadoId } from "./EmpleadoId";

export interface EmpleadoRepository {
    create(empleado: Empleado): Promise<void>
    getAll(): Promise<Empleado[]>
    getOneById(empleadoId: EmpleadoId): Promise<Empleado | null>
    update(empleado: Empleado): Promise<void>
    delete(empleadoId: EmpleadoId): Promise<void>
}