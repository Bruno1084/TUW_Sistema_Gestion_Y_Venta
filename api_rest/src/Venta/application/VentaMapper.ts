import type { ClienteNombre } from "../../Cliente/domain/ClienteNombre";
import type { UsuarioNombre } from "../../Usuario/domain/UsuarioNombre";
import type { Venta } from "../domain/Venta";
import type { VentaDetailDTO, VentaDetalleDTO, VentaDTO } from "./VentaDTO";

export class CompraMapper {
    static toDTO(venta:Venta, cliente: ClienteNombre, usuario: UsuarioNombre): VentaDTO {
        return {
            id: venta.id.value,
            precioTotal: venta.precioTotal.value,
            fechaCreacion: venta.fechaCreacion.value,
            cliente: {
                id: venta.clienteId.value,
                nombre: cliente.value
            },
            usuario: {
                id: venta.usuarioId.value,
                nombre: usuario.value
            }
        };
    }

    static toDetailDTO(venta: Venta, cliente: ClienteNombre, usuario: UsuarioNombre, detalles: VentaDetalleDTO[]): VentaDetailDTO {
        return {
            id: venta.id.value,
            precioTotal: venta.precioTotal.value,
            fechaCreacion: venta.fechaCreacion.value,
            cliente: {
                id: venta.clienteId.value,
                nombre: cliente.value,
            },
            usuario: {
                id: venta.usuarioId.value,
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
                    rubro: {
                        id: detalle.producto.rubro.id,
                        nombre: detalle.producto.rubro.nombre,
                        fechaCreacion: detalle.producto.rubro.fechaCreacion,
                        fechaModificacion: detalle.producto.rubro.fechaModificacion
                    },
                    marca: {
                        id: detalle.producto.marca.id,
                        nombre: detalle.producto.marca.nombre,
                        fechaCreacion: detalle.producto.marca.fechaCreacion,
                        fechaModificacion: detalle.producto.marca.fechaModificacion
                    },
                    imgUri: detalle.producto.imgUri
                }
            }))
        };
    }
}