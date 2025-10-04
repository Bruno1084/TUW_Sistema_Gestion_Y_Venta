import type { VentaDetailDTO, VentaSimpleDTO } from "./VentaDTO";
import type { Venta } from "../domain/Venta";
import type { EmpleadoNombre } from "../../Empleado/domain/EmpleadoNombre";
import type { ClienteNombre } from "../../Cliente/domain/ClienteNombre";

export class VentaMapper {
    static toDetailDTO(venta: Venta, cliente: ClienteNombre, empleado: EmpleadoNombre): VentaDetailDTO {
        return {
            id: venta.id.value,
            cliente: {
                id: venta.clienteId.value,
                nombre: cliente.value
            },
            empleado: {
                id: venta.empleadoId.value,
                nombre: empleado.value
            },
            precioTotal: venta.precioTotal.value,
            fechaCreacion: venta.fechaCreacion.value
        }
    }

    static toSimpleDTO(venta: Venta): VentaSimpleDTO {
        return {
            id: venta.id.value,
            clienteId: venta.clienteId.value,
            empleadoId: venta.empleadoId.value,
            precioTotal: venta.precioTotal.value,
            fechaCreacion: venta.fechaCreacion.value
        }
    }
}