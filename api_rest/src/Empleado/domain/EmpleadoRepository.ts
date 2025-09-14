import { Empleado } from "./Empleado";
import { EmpleadoId } from "./EmpleadoId";

export interface EmpleadoRepository {
    create(empleado: Empleado): Promise<Empleado>
    getAll(): Promise<Empleado[]>
    getOneById(empleadoId: EmpleadoId): Promise<Empleado | null>
    update(empleado: Empleado): Promise<Empleado>
    delete(empleadoId: EmpleadoId): Promise<void>
}