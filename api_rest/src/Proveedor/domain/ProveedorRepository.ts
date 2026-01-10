import type { ProveedorDTO, ProveedorSimpleDTO } from "../application/ProveedorDTO";
import type { Proveedor } from "./Proveedor";
import type { ProveedorId } from "./ProveedorId";

export interface ProveedorRepository {
    create(proveedor: Proveedor): Promise<ProveedorDTO>;
    getAll(): Promise<ProveedorDTO[]>;
    getOneById(proveedorId: ProveedorId): Promise<ProveedorDTO | null>;
    update(proveedor: Proveedor): Promise<ProveedorDTO>
    delete(proveedorId: ProveedorId): Promise<void>;
    findByNames(nombres: string[]): Promise<ProveedorSimpleDTO[]>;
}