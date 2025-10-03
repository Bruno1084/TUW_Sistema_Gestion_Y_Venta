export interface CompraDetailDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedor: {
        id: number;
        nombre: string;
    };
    empleado: {
        id: number;
        nombre: string;
    }
}

export interface CompraSimpleDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedorId: number;
    empleadoId: number;
}