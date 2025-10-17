import type { CompraDetailDTO, CompraSimpleDTO } from "./CompraDTO";
import type { Compra } from "../domain/Compra";
import type { Proveedor } from "../../Proveedor/domain/Proveedor";
import type { Usuario } from "../../Usuario/domain/Usuario";

export class CompraMapper {
    static toDetailDTO(compra: Compra, proveedor: Proveedor, usuario: Usuario): CompraDetailDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedor: {
                id: compra.proveedorId.value,
                nombre: proveedor.nombre.value,
                direccion: proveedor.direccion.value,
                telefono: proveedor.telefono.value,
                fechaCreacion: proveedor.fechaCreacion.value,
                fechaModificacion: proveedor.fechaModificacion.value
            },
            usuario: {
                id: compra.usuarioId.value,
                nombre: usuario.nombre.value
            }
        }
    }

    static toSimpleDTO(compra: Compra): CompraSimpleDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedorId: compra.proveedorId.value,
            usuarioId: compra.usuarioId.value
        }
    }
}