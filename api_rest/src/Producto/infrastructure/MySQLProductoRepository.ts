import type { ProductoRepository } from "../domain/ProductoRepository";
import type { Pool, ResultSetHeader, RowDataPacket } from "mysql2/promise";
import type { ProductoDetailDTO, ProductoSimpleDTO } from "../application/ProductoDTO";
import { Producto } from "../domain/Producto";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";

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
    es_activo: boolean;
}

export class MySQLProductoRepository implements ProductoRepository {
    private pool: Pool;

    constructor(pool: Pool) {
        this.pool = pool;
    }

    async create(producto: Producto): Promise<ProductoSimpleDTO> {
        const query = `
            INSERT INTO productos(codigo_barra, descripcion, id_proveedor, id_marca, id_rubro, precio_compra, precio_venta, stock, img_uri, fecha_creacion, fecha_modificacion, es_activo)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `;

        await this.pool.query<ResultSetHeader>(query, [
            producto.codigoBarra.value,
            producto.descripcion.value,
            producto.proveedorId.value,
            producto.marcaId.value,
            producto.rubroId.value,
            producto.precioCompra.value,
            producto.precioVenta.value,
            producto.stock.value,
            producto.imgUri.value,
            producto.fechaCreacion.value,
            producto.fechaModificacion.value,
            producto.esActivo.value
        ]);

        return this.getOneById(producto.codigoBarra) as Promise<ProductoSimpleDTO>;
    }

    async getAll(): Promise<ProductoSimpleDTO[]> {
        const query = `SELECT * FROM productos WHERE es_activo = true`;

        const [rows] = await this.pool.query<(RowDataPacket & MySQLProducto)[]>(query);

        return rows.map(
            (row) => ({
                codigoBarra: row!.codigo_barra,
                descripcion: row!.descripcion,
                precioCompra: row!.precio_compra,
                precioVenta: row!.precio_venta,
                stock: row!.stock,
                imgUri: row!.img_uri,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
                proveedorId: row!.id_proveedor,
                marcaId: row!.id_marca,
                rubroId: row!.id_rubro,
                esActivo: row!.es_activo
            })
        );
    }

    async getAllWithDetail(): Promise<ProductoDetailDTO[]> {
        const query = `
            SELECT 
            p.codigo_barra,
            p.descripcion,
            p.precio_venta,
            p.stock,
            m.id AS marca_id, m.nombre AS marca_nombre,
            pr.id AS proveedor_id, pr.nombre AS proveedor_nombre,
            r.id AS rubro_id, r.nombre AS rubro_nombre
            FROM productos p
            JOIN marcas m ON p.id_marca = m.id
            JOIN proveedores pr ON p.id_proveedor = pr.id
            JOIN rubros r ON p.id_rubro = r.id
            WHERE p.es_activo = true
        `;

        const [rows] = await this.pool.query<(RowDataPacket)[]>(query);

        return rows.map(
            (row) => ({
                codigoBarra: row!.codigo_barra,
                descripcion: row!.descripcion,
                precioCompra: row!.precio_compra,
                precioVenta: row!.precio_venta,
                stock: row!.stock,
                imgUri: row!.img_uri,
                fechaCreacion: row!.fecha_creacion,
                fechaModificacion: row!.fecha_modificacion,
                proveedor: {
                    id: row!.proveedor_id,
                    nombre: row!.proveedor_nombre,
                },
                marca: {
                    id: row!.marca_id,
                    nombre: row!.marca_nombre,
                },
                rubro: {
                    id: row!.rubro_id,
                    nombre: row!.rubro_nombre
                },
                esActivo: row!.es_activo
            })
        );
    }

    async getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoSimpleDTO | null> {
        const query = 'SELECT * FROM productos WHERE codigo_barra = ?';

        const [rows] = await this.pool.query<(MySQLProducto & RowDataPacket)[]>(query, [productoCodigoBarra.value]);

        if (rows.length === 0) {
            return null;
        }

        const row = rows[0];
        return {
            codigoBarra: row!.codigo_barra,
            descripcion: row!.descripcion,
            precioCompra: row!.precio_compra,
            precioVenta: row!.precio_venta,
            stock: row!.stock,
            imgUri: row!.img_uri,
            fechaCreacion: row!.fecha_creacion,
            fechaModificacion: row!.fecha_modificacion,
            proveedorId: row!.id_proveedor,
            marcaId: row!.id_marca,
            rubroId: row!.id_rubro,
            esActivo: row!.es_activo
        }
    }

    async update(producto: Producto): Promise<ProductoSimpleDTO> {
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

        return this.getOneById(producto.codigoBarra) as Promise<ProductoSimpleDTO>;
    }

    async delete(productocodigoBarra: ProductoCodigoBarra): Promise<void> {
        const query = 'UPDATE productos SET es_activo = false WHERE codigo_barra = ?';

        await this.pool.query(query, [productocodigoBarra.value]);
    }
}