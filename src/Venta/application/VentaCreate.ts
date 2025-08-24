import { Venta } from "../domain/Venta";
import type { VentaRepository } from "../domain/VentaRepository";
import { VentaId } from "../domain/VentaId";
import type { Empleado } from "../../Empleado/domain/Empleado";
import { VentaPrecioTotal } from "../domain/VentaPrecioTotal";
import { VentaFechaCreacion } from "../domain/VentaFechaCreacion";
import type { Cliente } from "../../Cliente/domain/Cliente";

export class VentaCreate {
    constructor(private repository: VentaRepository) { }

    async run(
        id: number,
        cliente: Cliente,
        empleado: Empleado,
        precioTotal: number,
        fechaCreacion: Date
    ): Promise<void> {
        const venta = new Venta(
            new VentaId(id),
            cliente,
            empleado,
            new VentaPrecioTotal(precioTotal),
            new VentaFechaCreacion(fechaCreacion)
        );

        await this.repository.create(venta);
    }
}