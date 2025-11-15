export interface VentaDetailDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    cliente: {
        id: number;
        nombre: string;
    };
    usuario: {
        id: number;
        nombre: string;
    },
    detalles: VentaDetalleDTO[];
}

export interface VentaDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    cliente: {
        id: number;
        nombre: string;
    };
    usuario: {
        id: number;
        nombre: string;
    };
}

export interface VentaDetalleDTO {
    cantidad: number;
    precioUnitario: number;
    precioTotal: number;
    producto: {
        codigoBarra: string;
        descripcion: string;
        precioCompra: number;
        precioVenta: number;
        stock: number;
        rubro: string;
        marca: string;
        imgUri: string;
    };
}