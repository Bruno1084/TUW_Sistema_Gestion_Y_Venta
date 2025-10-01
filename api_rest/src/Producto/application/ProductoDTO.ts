export interface ProductoDetailDTO {
    codigoBarra: string;
    descripcion: string;
    precioCompra: number;
    precioVenta: number;
    stock: number;
    imgUri: string;
    fechaCreacion: Date;
    fechaModificacion: Date;
    proveedor: {
        id: number;
        nombre: string;
    };
    marca: {
        id: number;
        nombre: string;
    };
    rubro: {
        id: number;
        nombre: string;
    };
    esActivo: boolean
}

export interface ProductoSimpleDTO {
    codigoBarra: string;
    descripcion: string;
    precioCompra: number;
    precioVenta: number;
    stock: number;
    imgUri: string;
    fechaCreacion: Date;
    fechaModificacion: Date;
    proveedorId: number;
    marcaId: number;
    rubroId: number;
    esActivo: boolean
}