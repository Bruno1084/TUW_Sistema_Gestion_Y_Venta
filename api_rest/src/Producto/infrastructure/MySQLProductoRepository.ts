import type { ProductoRepository } from "../domain/ProductoRepository";
import type { Pool, RowDataPacket } from "mysql2/promise";
import { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { ProductoStock } from "../domain/ProductoStock";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { MarcaId } from "../../Marca/domain/MarcaId";
import { RubroId } from "../../Rubro/domain/RubroId";
import { ProductoEsActivo } from "../domain/ProductoEsActivo";

type MySQLProducto = {
    codigo_barra: string;
    descripcion: string;
    precio_compra: number;
    precio_venta: number;
    stock: number;
    img_uri: string;
    fecha_creacion: Date;
    fecha_modificacion: Date;
    id_proveedor: number;
    id_marca: number;
    id_rubro: number;
}

export class MySQLProductoRepository implements ProductoRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(producto: Producto): Promise<void> {
        const query = `
            INSERT INTO productos(codigo_barra, descripcion, id_proveedor, id_marca, id_rubro, precio_compra, precio_venta, stock, img_uri, fecha_creacion, fecha_modificacion)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `;

        await this.pool.query(query, [
            producto.codigoBarra,
            producto.descripcion,
            producto.proveedorId.value,
            producto.marcaId,
            producto.rubroId,
            producto.precioCompra,
            producto.precioVenta,
            producto.stock,
            producto.imgUri,
            producto.fechaCreacion.value,
            producto.fechaModificacion.value
        ]);
    }

    async getAll(): Promise<Producto[]> {
        const query = `SELECT * FROM productos WHERE es_activo = true`;

        const [rows] = await this.pool.query<(MySQLProducto & RowDataPacket)[]>(query);

        return rows.map(
            (row) =>
                new Producto(
                    new ProductoCodigoBarra(row.codigo_barra),
                    new ProductoDescripcion(row.descripcion),
                    new ProductoPrecioCompra(row.precio_compra),
                    new ProductoPrecioVenta(row.precio_venta),
                    new ProductoStock(row.stock),
                    new ProductoImgUri(row.img_uri),
                    new ProductoFechaCreacion(row.fecha_creacion),
                    new ProductoFechaModificacion(row.fecha_modificacion),
                    new ProveedorId(row.id_proveedor),
                    new MarcaId(row.id_marca),
                    new RubroId(row.id_rubro),
                    new ProductoEsActivo(row.es_activo)
                )
        );
    }

    async getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<Producto | null> {
        const query = 'SELECT * FROM productos WHERE codigo_barra = ?';

        const [rows] = await this.pool.query<(MySQLProducto & RowDataPacket)[]>(query, [productoCodigoBarra.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return new Producto(
            new ProductoCodigoBarra(row!.codigo_barra),
            new ProductoDescripcion(row!.descripcion),
            new ProductoPrecioCompra(row!.precio_compra),
            new ProductoPrecioVenta(row!.precio_venta),
            new ProductoStock(row!.stock),
            new ProductoImgUri(row!.img_uri),
            new ProductoFechaCreacion(row!.fecha_creacion),
            new ProductoFechaModificacion(row!.fecha_modificacion),
            new ProveedorId(row!.id_proveedor),
            new MarcaId(row!.id_marca),
            new RubroId(row!.id_rubro),
            new ProductoEsActivo(row!.es_activo)
        );
    }

    async update(producto: Producto): Promise<void> {
        const query = `
            UPDATE productos SET
            descripcion = ?,
            precio_compra = ?,
            precio_venta = ?,
            stock = ?,
            img_uri = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            proveedor_id = ?,
            marca_id = ?,
            rubro_id = ?
            WHERE codigo_barra = ?
        `;

        await this.pool.query(query, [
            producto.descripcion.value,
            producto.precioCompra.value,
            producto.precioVenta.value,
            producto.stock.value,
            producto.imgUri.value,
            producto.fechaCreacion.value,
            producto.fechaModificacion.value,
            producto.proveedorId.value,
            producto.marcaId.value,
            producto.rubroId.value,
            producto.codigoBarra.value
        ]);
    }

    async delete(productocodigoBarra: ProductoCodigoBarra): Promise<void> {
        const query = 'UPDATE productos SET es_activo = false WHERE codigo_barra = ?';

        await this.pool.query(query, [productocodigoBarra.value]);
    }
}