import type { MarcaNombre } from "../../Marca/domain/MarcaNombre";
import type { ProveedorNombre } from "../../Proveedor/domain/ProveedorNombre";
import type { RubroNombre } from "../../Rubro/domain/RubroNombre";
import type { Producto } from "../domain/Producto";
import type { ProductoSimpleDTO, ProductoDetailDTO } from "./ProductoDTO";

export class ProductoMapper {
    static toDetailDTO(producto: Producto, marca: MarcaNombre, rubro: RubroNombre, proveedor: ProveedorNombre): ProductoDetailDTO {
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
                nombre: proveedor.value
            },
            marca: {
                id: producto.marcaId.value,
                nombre: marca.value
            },
            rubro: {
                id: producto.rubroId.value,
                nombre: rubro.value
            },
            esActivo: producto.esActivo.value
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
            rubroId: producto.rubroId.value
        }
    }
}