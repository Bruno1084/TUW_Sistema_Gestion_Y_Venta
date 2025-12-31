import type { ProductoDetailDTO } from "./ProductoDTO";
import type { ProductoRepository } from "../domain/ProductoRepository";
import type { ProveedorGetOneById } from "../../Proveedor/application/ProveedorGetOneById";
import type { MarcaGetOneById } from "../../Marca/application/MarcaGetOneById";
import type { RubroGetOneById } from "../../Rubro/application/RubroGetOneById";
import { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { ProductoStock } from "../domain/ProductoStock";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { MarcaId } from "../../Marca/domain/MarcaId";
import { RubroId } from "../../Rubro/domain/RubroId";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";

export class ProductoUpdate {
    constructor(
        private productoRepository: ProductoRepository,
        private proveedorGetOneById: ProveedorGetOneById,
        private marcaGetOneById: MarcaGetOneById,
        private rubroGetOneById: RubroGetOneById
    ) { }

    async run(
        codigoBarra: string,
        updates: {
            descripcion?: string;
            precioCompra?: number;
            precioVenta?: number;
            stock?: number;
            imgUri?: string;
            proveedorId?: number,
            marcaId?: number,
            rubroId?: number,
        }
    ): Promise<ProductoDetailDTO> {
        const productoExistente = await this.productoRepository.getOneById(new ProductoCodigoBarra(codigoBarra));
        if (!productoExistente) throw new Error("Producto no encontrado");

        if (updates.proveedorId) {
            const proveedorExistente = await this.proveedorGetOneById.run(updates.proveedorId);
            if (!proveedorExistente) throw new Error("Proveedor no encontrado");
        }

        if (updates.marcaId) {
            const marcaExistente = await this.marcaGetOneById.run(updates.marcaId);
            if (!marcaExistente) throw new Error("Marca no encontrada");
        }

        if (updates.rubroId) {
            const rubroExistente = await this.rubroGetOneById.run(updates.rubroId);
            if (!rubroExistente) throw new Error("Rubro no encontrado");
        }

        const productoActualizado = new Producto(
            new ProductoCodigoBarra(productoExistente.codigoBarra),
            updates.descripcion ? new ProductoDescripcion(updates.descripcion) : new ProductoDescripcion(productoExistente.descripcion),
            updates.precioCompra !== undefined ? new ProductoPrecioCompra(updates.precioCompra) : new ProductoPrecioCompra(productoExistente.precioCompra),
            updates.precioVenta !== undefined ? new ProductoPrecioVenta(updates.precioVenta) : new ProductoPrecioVenta(productoExistente.precioVenta),
            updates.stock !== undefined ? new ProductoStock(updates.stock) : new ProductoStock(productoExistente.stock),
            updates.imgUri ? new ProductoImgUri(updates.imgUri) : new ProductoImgUri(productoExistente.imgUri),
            new ProductoFechaCreacion(productoExistente.fechaCreacion),
            new ProductoFechaModificacion(new Date()),
            new ProveedorId(updates.proveedorId!),
            new MarcaId(updates.marcaId!),
            new RubroId(updates.rubroId!),
        );

        return await this.productoRepository.update(productoActualizado);
    }
}