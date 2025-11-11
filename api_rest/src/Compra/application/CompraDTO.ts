export interface CompraDetailDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedor: {
        id: number;
        nombre: string;
    };
    usuario: {
        id: number;
        nombre: string;
    },
    detalles: CompraDetalleDTO[];
}

export interface CompraDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedor: {
        id: number;
        nombre: string;
    };
    usuario: {
        id: number;
        nombre: string;
    };
}

export interface CompraDetalleDTO {
    cantidad: number;
    precioUnitario: number;
    precioTotal: number;
    producto: {
        codigoBarra: string;
        descripcion: string;
        precioCompra: number;
        precioVenta: number;
        stock: number;
        imgUri: string;
    };
}