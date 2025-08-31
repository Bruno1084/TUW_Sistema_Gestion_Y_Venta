import type { ProductoCodigoBarra } from "./ProductoCodigoBarra";
import type { ProductoDescripcion } from "./ProductoDescripcion";
import type { ProductoFechaCreacion } from "./ProductoFechaCreacion";
import type { ProductoImgUri } from "./ProductoImgUri";
import type { ProductoNombre } from "./ProductoNombre";
import type { ProductoPrecioCompra } from "./ProductoPrecioCompra";
import type { ProductoPrecioVenta } from "./ProductoPrecioVenta";
import type { ProductoStock } from "./ProductoStock";
import type { ProductoFechaModificacion } from "./ProductoFechaModificacion";
import type { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import type { MarcaId } from "../../Marca/domain/MarcaId";
import type { RubroId } from "../../Rubro/domain/RubroId";

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
    proveedorId: ProveedorId;
    marcaId: MarcaId;
    rubroId: RubroId;

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
        proveedorId: ProveedorId,
        marcaId: MarcaId,
        rubroId: RubroId
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
        this.proveedorId = proveedorId;
        this.marcaId = marcaId;
        this.rubroId = rubroId;
    }
}