import type { CompraRepository } from "../domain/CompraRepository";
import type { EmpleadoId } from "../../Empleado/domain/EmpleadoId";
import type { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { Compra } from "../domain/Compra";
import { CompraFechaCreacion } from "../domain/CompraFechaCreacion";
import { CompraId } from "../domain/CompraId";
import { CompraPrecioTotal } from "../domain/CompraPrecioTotal";

export class CompraCreate {
    constructor(private repository: CompraRepository) { }

    async run(
        precioTotal: number,
        fechaCreacion: Date,
        proveedorId: ProveedorId,
        empleadoId: EmpleadoId
    ): Promise<Compra> {
        const compra = new Compra(
            new CompraId(0),
            new CompraPrecioTotal(precioTotal),
            new CompraFechaCreacion(fechaCreacion),
            proveedorId,
            empleadoId
        );

        return await this.repository.create(compra);
    }
}