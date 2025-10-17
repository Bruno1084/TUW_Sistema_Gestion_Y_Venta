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
    usuario: {
        id: number;
        nombre: string;
    }
}

export interface CompraSimpleDTO {
    id: number;
    precioTotal: number;
    fechaCreacion: Date;
    proveedorId: number;
    usuarioId: number;
}