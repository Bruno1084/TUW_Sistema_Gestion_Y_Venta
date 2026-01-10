import type { ProductoSimpleDTO, ProductoDetailDTO } from "./ProductoDTO";
import type { Marca } from "../../Marca/domain/Marca";
import type { Proveedor } from "../../Proveedor/domain/Proveedor";
import type { Rubro } from "../../Rubro/domain/Rubro";
import type { Producto } from "../domain/Producto";

export class ProductoMapper {
    static toDetailDTO(producto: Producto, marca: Marca, rubro: Rubro, proveedor: Proveedor): ProductoDetailDTO {
        return {
            codigoBarra: producto.codigoBarra.value,
            descripcion: producto.descripcion.value,
            precioCompra: producto.precioCompra.value,
            precioVenta: producto.precioVenta.value,
            stock: producto.stock.value,
            imgUri: producto.imgUri.value,
            fechaCreacion: producto.fechaCreacion.value,
            fechaModificacion: producto.fechaModificacion.value,
            proveedor: {
                id: producto.proveedorId.value,
                nombre: proveedor.nombre.value,
                direccion: proveedor.direccion.value,
                telefono: proveedor.telefono.value,
                fechaCreacion: proveedor.fechaCreacion.value,
                fechaModificacion: proveedor.fechaModificacion.value
            },
            marca: {
                id: producto.marcaId.value,
                nombre: marca.nombre.value,
                fechaCreacion: marca.fechaCreacion.value,
                fechaModificacion: marca.fechaModificacion.value
            },
            rubro: {
                id: producto.rubroId.value,
                nombre: rubro.nombre.value,
                fechaCreacion: rubro.fechaCreación.value,
                fechaModificacion: rubro.fechaModificacion.value
            },
        }
    }

    static toSimpleDTO (producto: Producto): ProductoSimpleDTO {
        return {
            codigoBarra: producto.codigoBarra.value,
            descripcion: producto.descripcion.value,
            precioCompra: producto.precioCompra.value,
            precioVenta: producto.precioVenta.value,
            stock: producto.stock.value,
            imgUri: producto.imgUri.value,
            fechaCreacion: producto.fechaCreacion.value,
            fechaModificacion: producto.fechaModificacion.value,
            proveedorId: producto.proveedorId.value,
            marcaId: producto.marcaId.value,
            rubroId: producto.rubroId.value,
        }
    }
}