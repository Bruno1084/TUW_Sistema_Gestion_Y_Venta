import { MarcaId } from "../../Marca/domain/MarcaId";
import { MarcaNombre } from "../../Marca/domain/MarcaNombre";
import { ProveedorId } from "../../Proveedor/domain/ProveedorId";
import { ProveedorNombre } from "../../Proveedor/domain/ProveedorNombre";
import { RubroId } from "../../Rubro/domain/RubroId";
import { RubroNombre } from "../../Rubro/domain/RubroNombre";
import { ProductoCodigoBarra } from "../domain/ProductoCodigoBarra";
import { ProductoDescripcion } from "../domain/ProductoDescripcion";
import { ProductoFechaCreacion } from "../domain/ProductoFechaCreacion";
import { ProductoFechaModificacion } from "../domain/ProductoFechaModificacion";
import { ProductoImgUri } from "../domain/ProductoImgUri";
import { ProductoPrecioCompra } from "../domain/ProductoPrecioCompra";
import { ProductoPrecioVenta } from "../domain/ProductoPrecioVenta";
import { ProductoStock } from "../domain/ProductoStock";

export class ProductoDTO {
    productoCodigoBarra: ProductoCodigoBarra;
    productoDescripcion: ProductoDescripcion;
    productoPrecioCompra: ProductoPrecioCompra;
    productoPrecioVenta: ProductoPrecioVenta;
    productoStock: ProductoStock;
    productoImgUri: ProductoImgUri;
    productoFechaCreacion: ProductoFechaCreacion;
    productoFechaModificacion: ProductoFechaModificacion;
    productoProveedorId: ProveedorId;
    productoProveedorNombre: ProveedorNombre;
    productoMarcaId: MarcaId;
    productoMarcaNombre: MarcaNombre;
    productoRubroId: RubroId;
    productoRubroNombre: RubroNombre;

    public constructor(
        codigoBarra: string,
        descripcion: string,
        precioCompra: number,
        precioVenta: number,
        stock: number,
        imgUri: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
        proveedorId: number,
        proveedorNombre: string,
        marcaId: number,
        marcaNombre: string,
        rubroId: number,
        rubroNombre: string
    ) {
        this.productoCodigoBarra = new ProductoCodigoBarra(codigoBarra);
        this.productoDescripcion = new ProductoDescripcion(descripcion);
        this.productoPrecioCompra = new ProductoPrecioCompra(precioCompra);
        this.productoPrecioVenta = new ProductoPrecioVenta(precioVenta);
        this.productoStock = new ProductoStock(stock);
        this.productoImgUri = new ProductoImgUri(imgUri);
        this.productoFechaCreacion = new ProductoFechaCreacion(fechaCreacion);
        this.productoFechaModificacion = new ProductoFechaModificacion(fechaModificacion);
        this.productoProveedorId = new ProveedorId(proveedorId);
        this.productoProveedorNombre = new ProveedorNombre(proveedorNombre);
        this.productoMarcaId = new MarcaId(marcaId);
        this.productoMarcaNombre = new MarcaNombre(marcaNombre);
        this.productoRubroId = new RubroId(rubroId);
        this.productoRubroNombre = new RubroNombre(rubroNombre);
    }
}