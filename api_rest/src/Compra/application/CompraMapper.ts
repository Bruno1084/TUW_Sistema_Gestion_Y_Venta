import type { CompraDetailDTO, CompraSimpleDTO } from "./CompraDTO";
import type { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import type { ProveedorNombre } from "../../Proveedor/domain/ProveedorNombre";
import type { Compra } from "../domain/Compra";

export class CompraMapper {
    static toDetailDTO(compra: Compra, proveedor: ProveedorNombre, empleado: EmpleadoNombre): CompraDetailDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedor: {
                id: compra.proveedorId.value,
                nombre: proveedor.value
            },
            empleado: {
                id: compra.empleadoId.value,
                nombre: empleado.value
            }
        }
    }

    static toSimpleDTO(compra: Compra): CompraSimpleDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedorId: compra.proveedorId.value,
            empleadoId: compra.empleadoId.value
        }
    }
}