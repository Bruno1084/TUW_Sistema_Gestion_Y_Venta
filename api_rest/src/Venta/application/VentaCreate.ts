import type { VentaRepository } from "../domain/VentaRepository";
import { Venta } from "../domain/Venta";
import { VentaId } from "../domain/VentaId";
import { VentaPrecioTotal } from "../domain/VentaPrecioTotal";
import { VentaFechaCreacion } from "../domain/VentaFechaCreacion";
import { ClienteId } from "../../Cliente/domain/ClienteId";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";

export class VentaCreate {
    constructor(private repository: VentaRepository) { }

    async run(
        clienteId: number,
        empleadoId: number,
        precioTotal: number,
        fechaCreacion: Date
    ): Promise<Venta> {
        const venta = new Venta(
            new VentaId(0),
            new ClienteId(clienteId),
            new EmpleadoId(empleadoId),
            new VentaPrecioTotal(precioTotal),
            new VentaFechaCreacion(fechaCreacion)
        );

        return await this.repository.create(venta);
    }
}