import type { EmpleadoDTO } from "../application/EmpleadoDTO";
import { Empleado } from "./Empleado";
import { EmpleadoId } from "./EmpleadoId";

export interface EmpleadoRepository {
    create(empleado: Empleado): Promise<EmpleadoDTO>
    getAll(): Promise<EmpleadoDTO[]>
    getOneById(empleadoId: EmpleadoId): Promise<EmpleadoDTO | null>
    update(empleado: Empleado): Promise<EmpleadoDTO>
    delete(empleadoId: EmpleadoId): Promise<void>
}