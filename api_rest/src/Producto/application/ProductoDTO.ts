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
        direccion: string;
        telefono: string;
        fechaCreacion: Date;
        fechaModificacion: Date;
    };
    marca: {
        id: number;
        nombre: string;
        fechaCreacion: Date;
        fechaModificacion: Date;
    };
    rubro: {
        id: number;
        nombre: string;
        fechaCreacion: Date;
        fechaModificacion: Date;
    };
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
}

export interface ProductoExcelRowDTO {
    "Código de Barras": string;
    "Descripción": string;
    "Precio de Compra": string;
    "Precio de Venta": string;
    "Stock": number;
    "Imagen Uri": string;
    "Proveedor": string;
    "Marca": string;
    "Rubro": string;    
}