export interface EmpleadoDTO {
    id: number;
    nombre: string;
    direccion: string;
    telefono: string;
    fechaCreacion: Date;
    fechaModificacion: Date;
    esActivo: boolean;
}