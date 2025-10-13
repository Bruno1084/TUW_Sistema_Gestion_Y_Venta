import type { CompraRepository } from "../domain/CompraRepository";
import type { CompraSimpleDTO } from "./CompraDTO";
import { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { Compra } from "../domain/Compra";
import { CompraFechaCreacion } from "../domain/CompraFechaCreacion";
import { CompraId } from "../domain/CompraId";
import { CompraPrecioTotal } from "../domain/CompraPrecioTotal";

export class CompraCreate {
    constructor(private repository: CompraRepository) { }

    async run(
        precioTotal: number,
        fechaCreacion: Date,
        proveedorId: number,
        empleadoId: number
    ): Promise<CompraSimpleDTO> {
        const compra = new Compra(
            new CompraId(0),
            new CompraPrecioTotal(precioTotal),
            new CompraFechaCreacion(fechaCreacion),
            new ProveedorId(proveedorId),
            new EmpleadoId(empleadoId)
        );

        return await this.repository.create(compra);
    }
}