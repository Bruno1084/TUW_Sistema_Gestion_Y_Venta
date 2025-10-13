export interface CompraDetailDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedor: {
        id: number;
        nombre: string;
        direccion: string;
        telefono: string;
        fechaCreacion: Date;
        fechaModificacion: Date;
    };
    empleado: {
        id: number;
        nombre: string;
        direccion: string;
        telefono: string;
        fechaCreacion: Date;
        fechaModificacion: Date;
    }
}

export interface CompraSimpleDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedorId: number;
    empleadoId: number;
}