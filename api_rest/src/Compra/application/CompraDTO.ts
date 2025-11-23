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

export interface CompraReporteByProveedoresDTO {
    proveedorId: number;
    proveedorNombre: string;
    comprasTotales: number;
    precioTotal: number;
}