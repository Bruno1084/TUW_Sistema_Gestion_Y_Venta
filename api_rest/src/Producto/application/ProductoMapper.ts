import type { MarcaNombre } from "../../Marca/domain/MarcaNombre";
import type { ProveedorNombre } from "../../Proveedor/domain/ProveedorNombre";
import type { RubroNombre } from "../../Rubro/domain/RubroNombre";
import type { Producto } from "../domain/Producto";
import type { ProductoDTO } from "./ProductoDTO";

export class ProductoMapper {
    static toDTO(producto: Producto, marca: MarcaNombre, rubro: RubroNombre, proveedor: ProveedorNombre): ProductoDTO {
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
}