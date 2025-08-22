import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";
import type { ProductoDescripcion } from "./ProductoDescripcion";
import type { ProductoFechaCreacion } from "./ProductoFechaCreacion";
import type { ProductoImgUri } from "./ProductoImgUri";
import type { ProductoNombre } from "./ProductoNombre";
import type { ProductoPrecioCompra } from "./ProductoPrecioCompra";
import type { ProductoPrecioVenta } from "./ProductoPrecioVenta";
import type { ProductoStock } from "./ProductoStock";
import type { ProductoFechaModificacion } from "./ProductoFechaModificacion";
import { Proveedor } from "../../Proveedor/domain/Proveedor";
import { Marca } from "../../Marca/domain/Marca";
import { Rubro } from "../../Rubro/domain/Rubro";

export class Producto {
    codigoBarra: ProductoCodigoBarra;
    nombre: ProductoNombre;
    descripcion: ProductoDescripcion;
    precioCompra: ProductoPrecioCompra;
    precioVenta: ProductoPrecioVenta;
    stock: ProductoStock;
    imgUri: ProductoImgUri;
    fechaCreacion: ProductoFechaCreacion;
    fechaModificacion: ProductoFechaModificacion;
    proveedor: Proveedor;
    marca: Marca;
    rubro: Rubro;

    constructor(
        codigoBarra: ProductoCodigoBarra,
        nombre: ProductoNombre,
        descripcion: ProductoDescripcion,
        precioCompra: ProductoPrecioCompra,
        precioVenta: ProductoPrecioVenta,
        stock: ProductoStock,
        imgUri: ProductoImgUri,
        fechaCreacion: ProductoFechaCreacion,
        fechaModificacion: ProductoFechaModificacion,
        proveedor: Proveedor,
        marca: Marca,
        rubro: Rubro
    ) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.imgUri = imgUri;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.proveedor = proveedor;
        this.marca = marca;
        this.rubro = rubro;
    }
}