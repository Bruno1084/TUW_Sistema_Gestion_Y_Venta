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
        rubro: {
            id: number;
            nombre: string;
            fechaCreacion: Date;
            fechaModificacion: Date;
        };
        marca: {
            id: number;
            nombre: string;
            fechaCreacion: Date;
            fechaModificacion: Date;
        };
        imgUri: string;
    };
}

export interface VentaReporteByClientesDTO {
    clienteId: number;
    clienteNombre: string;
    ventasTotales: number;
    precioTotal: number;
}

export interface VentaReporteByProductosDTO {
    codigoBarra: string;
    descripcion: string;
    cantidadVendida: number;
    precioTotal: number;
}