import { Empleado } from "../domain/Empleado";
import { EmpleadoId } from "../domain/EmpleadoId";
import { type EmpleadoRepository } from "../domain/EmpleadoRepository";

export class InMemoryEmpleadoRepository implements EmpleadoRepository{
    private empleados: Empleado[] = [];

    async create(empleado: Empleado): Promise<void> {
        this.empleados.push(empleado);
    }

    async getAll(): Promise<Empleado[]> {
        return this.empleados;
    }

    async getOneById(empleadoId: EmpleadoId): Promise<Empleado | null> {
        return this.empleados.find((empleado) => empleado.id.value === empleadoId.value)   || null;
    }

    async update(empleado: Empleado): Promise<void> {
        const index = this.empleados.findIndex((u) => u.id.value == empleado.id.value);
        this.empleados[index] = empleado
    }

    async delete(empleadoId: EmpleadoId): Promise<void> {
        this.empleados = this.empleados.filter((empleado) => empleado.id.value !== empleadoId.value);
    }
}