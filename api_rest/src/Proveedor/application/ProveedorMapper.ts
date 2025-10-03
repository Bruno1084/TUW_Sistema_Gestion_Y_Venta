import type { Proveedor } from "../domain/Proveedor";
import type { ProveedorDTO } from "./ProveedorDTO";

export class ProveedorMapper {
    static toDTO(proveedor: Proveedor): ProveedorDTO {
        return {
            id: proveedor.id.value,
            nombre: proveedor.nombre.value,
            direccion: proveedor.direccion.value,
            telefono: proveedor.telefono.value,
            fechaCreacion: proveedor.fechaCreacion.value,
            fechaModificacion: proveedor.fechaModificacion.value,
            esActivo: proveedor.esActivo.value
        }
    }
}