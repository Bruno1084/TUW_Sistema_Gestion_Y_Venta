export interface ProveedorDTO {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string,
    fechaCreacion: Date,
    fechaModificacion: Date,
}

export interface ProveedorSimpleDTO {
    id: number;
    nombre: string;
}