import type { ProveedorNombre } from "../../Proveedor/domain/ProveedorNombre";
import type { UsuarioNombre } from "../../Usuario/domain/UsuarioNombre";
import type { Compra } from "../domain/Compra";
import type { CompraDetailDTO, CompraDetalleDTO, CompraDTO } from "./CompraDTO";

export class CompraMapper {
    static toDTO(compra:Compra, proveedor: ProveedorNombre, usuario: UsuarioNombre): CompraDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedor: {
                id: compra.proveedorId.value,
                nombre: proveedor.value
            },
            usuario: {
                id: compra.usuarioId.value,
                nombre: usuario.value
            }
        };
    }

    static toDetailDTO(compra: Compra, proveedor: ProveedorNombre, usuario: UsuarioNombre, detalles: CompraDetalleDTO[]): CompraDetailDTO {
        return {
            id: compra.id.value,
            precioTotal: compra.precioTotal.value,
            fechaCreacion: compra.fechaCreacion.value,
            proveedor: {
                id: compra.proveedorId.value,
                nombre: proveedor.value,
            },
            usuario: {
                id: compra.usuarioId.value,
                nombre: usuario.value
            },
            detalles: detalles.map(detalle => ({
                cantidad: detalle.cantidad,
                precioUnitario: detalle.precioUnitario,
                precioTotal: detalle.precioTotal,
                producto: {
                    codigoBarra: detalle.producto.codigoBarra,
                    descripcion: detalle.producto.descripcion,
                    precioCompra: detalle.producto.precioCompra,
                    precioVenta: detalle.producto.precioVenta,
                    stock: detalle.producto.stock,
                    imgUri: detalle.producto.imgUri
                }
            }))
        };
    }
}