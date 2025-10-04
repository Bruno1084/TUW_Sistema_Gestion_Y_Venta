export interface VentaDetailDTO {
    id: number;
    cliente: {
        id: number;
        nombre: string;
    }
    empleado: {
        id: number;
        nombre: string;
    }
    precioTotal: number;
    fechaCreacion: Date;
}

export interface VentaSimpleDTO {
    id: number;
    clienteId: number;
    empleadoId: number;
    precioTotal: number;
    fechaCreacion: Date;
}
