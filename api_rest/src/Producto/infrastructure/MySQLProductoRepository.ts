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

    async create(producto: Producto): Promise<ProductoDetailDTO> {
        const query = `
            INSERT INTO productos(codigo_barra, descripcion, id_proveedor, id_marca, id_rubro, precio_compra, precio_venta, stock, img_uri, fecha_creacion, fecha_modificacion)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
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
        ]);

        return this.getOneByIdWithDetail(producto.codigoBarra) as Promise<ProductoDetailDTO>;
    }

    async getAll(): Promise<ProductoSimpleDTO[]> {
        const query = `
        SELECT
        codigo_barra,
        descripcion,
        precio_compra,
        precio_venta,
        stock,
        img_uri,
        fecha_creacion,
        fecha_modificacion,
        id_proveedor,
        id_marca,
        id_rubro
        FROM productos WHERE es_activo = true`;

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
            })
        );
    }

    async getAllWithDetail(): Promise<ProductoDetailDTO[]> {
        const query = `
            SELECT 
            p.codigo_barra,
            p.descripcion,
            p.precio_compra,
            p.precio_venta,
            p.stock,
            p.img_uri,
            p.fecha_creacion,
            p.fecha_modificacion,
            m.id AS marca_id,
            m.nombre AS marca_nombre,
            m.fecha_creacion AS marca_fecha_creacion,
            m.fecha_modificacion AS marca_fecha_modificacion,
            pr.id AS proveedor_id,
            pr.nombre AS proveedor_nombre,
            pr.fecha_creacion AS proveedor_fecha_creacion,
            pr.fecha_modificacion AS proveedor_fecha_modificacion,
            r.id AS rubro_id,
            r.nombre AS rubro_nombre,
            r.fecha_creacion AS rubro_fecha_creacion,
            r.fecha_modificacion AS rubro_fecha_modificacion
            FROM productos p
            JOIN marcas m ON p.id_marca = m.id
            JOIN proveedores pr ON p.id_proveedor = pr.id
            JOIN rubros r ON p.id_rubro = r.id
            WHERE p.es_activo = true
        `;

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
                proveedor: {
                    id: row!.proveedor_id,
                    nombre: row!.proveedor_nombre,
                    direccion: row!.proveedor_direccion,
                    telefono: row!.proveedor_telefono,
                    fechaCreacion: row!.proveedor_fecha_creacion,
                    fechaModificacion: row!.proveedor_fecha_modificacion
                },
                marca: {
                    id: row!.marca_id,
                    nombre: row!.marca_nombre,
                    fechaCreacion: row!.marca_fecha_creacion,
                    fechaModificacion: row!.marca_fecha_modificacion
                },
                rubro: {
                    id: row!.rubro_id,
                    nombre: row!.rubro_nombre,
                    fechaCreacion: row!.rubro_fecha_creacion,
                    fechaModificacion: row!.rubro_fecha_modificacion
                },
            })
        );
    }

    async getOneById(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoSimpleDTO | null> {
        const query = `
        SELECT
        codigo_barra,
        descripcion,
        precio_compra,
        precio_venta,
        stock,
        img_uri,
        fecha_creacion,
        fecha_modificacion,
        id_proveedor,
        id_marca,
        id_rubro
        FROM productos WHERE codigo_barra = ?`;

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
        }
    }

    async getOneByIdWithDetail(productoCodigoBarra: ProductoCodigoBarra): Promise<ProductoDetailDTO | null> {
        const query = `
        SELECT 
        p.codigo_barra,
        p.descripcion,
        p.precio_compra,
        p.precio_venta,
        p.stock,
        p.img_uri,
        p.fecha_creacion,
        p.fecha_modificacion,
        m.id AS marca_id,
        m.nombre AS marca_nombre,
        m.fecha_creacion AS marca_fecha_creacion,
        m.fecha_modificacion AS marca_fecha_modificacion,
        pr.id AS proveedor_id,
        pr.nombre AS proveedor_nombre,
        pr.fecha_creacion AS proveedor_fecha_creacion,
        pr.fecha_modificacion AS proveedor_fecha_modificacion,
        r.id AS rubro_id,
        r.nombre AS rubro_nombre,
        r.fecha_creacion AS rubro_fecha_creacion,
        r.fecha_modificacion AS rubro_fecha_modificacion
        FROM productos p
        JOIN marcas m ON p.id_marca = m.id
        JOIN proveedores pr ON p.id_proveedor = pr.id
        JOIN rubros r ON p.id_rubro = r.id
        WHERE p.codigo_barra = ?
        `;

        const [rows] = await this.pool.query<(MySQLProducto & RowDataPacket)[]>(query, [productoCodigoBarra.value]);

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
            proveedor: {
                id: row!.proveedor_id,
                nombre: row!.proveedor_nombre,
                direccion: row!.proveedor_direccion,
                telefono: row!.proveedor_telefono,
                fechaCreacion: row!.proveedor_fecha_creacion,
                fechaModificacion: row!.proveedor_fecha_modificacion
            },
            marca: {
                id: row!.marca_id,
                nombre: row!.marca_nombre,
                fechaCreacion: row!.marca_fecha_creacion,
                fechaModificacion: row!.marca_fecha_modificacion
            },
            rubro: {
                id: row!.rubro_id,
                nombre: row!.rubro_nombre,
                fechaCreacion: row!.rubro_fecha_creacion,
                fechaModificacion: row!.rubro_fecha_modificacion
            }
        }
    }

    async update(producto: Producto): Promise<ProductoDetailDTO> {
        const query = `
            UPDATE productos SET
            descripcion = ?,
            precio_compra = ?,
            precio_venta = ?,
            stock = ?,
            img_uri = ?,
            fecha_creacion = ?,
            fecha_modificacion = ?,
            id_proveedor = ?,
            id_marca = ?,
            id_rubro = ?
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

        return this.getOneByIdWithDetail(producto.codigoBarra) as Promise<ProductoDetailDTO>;
    }

    async delete(productocodigoBarra: ProductoCodigoBarra): Promise<void> {
        const query = 'UPDATE productos SET es_activo = false WHERE codigo_barra = ?';

        await this.pool.query(query, [productocodigoBarra.value]);
    }

    async importXlsx(productos: Producto[]): Promise<void> {
        if (productos.length === 0) return;

        const query = `
        INSERT INTO productos (
            codigo_barra,
            descripcion,
            id_proveedor,
            id_marca,
            id_rubro,
            precio_compra,
            precio_venta,
            stock,
            img_uri,
            fecha_creacion,
            fecha_modificacion,
            es_activo
        )
        VALUES ?
        ON DUPLICATE KEY UPDATE
            descripcion = VALUES(descripcion),
            id_proveedor = VALUES(id_proveedor),
            id_marca = VALUES(id_marca),
            id_rubro = VALUES(id_rubro),
            precio_compra = VALUES(precio_compra),
            precio_venta = VALUES(precio_venta),
            stock = VALUES(stock),
            img_uri = VALUES(img_uri),
            fecha_modificacion = VALUES(fecha_modificacion),
            es_activo = true
    `;

        const values = productos.map(p => [
            p.codigoBarra.value,
            p.descripcion.value,
            p.proveedorId.value,
            p.marcaId.value,
            p.rubroId.value,
            p.precioCompra.value,
            p.precioVenta.value,
            p.stock.value,
            p.imgUri.value,
            p.fechaCreacion.value,
            p.fechaModificacion.value
        ]);

        await this.pool.query(query, [values]);
    }
}