import type { Proveedor } from "./Proveedor";
import type { ProveedorId } from "./ProveedorId";

export interface ProveedorRepository {
    create(proveedor: Proveedor): Promise<Proveedor>;
    getAll(): Promise<Proveedor[]>;
    getOneById(proveedorId: ProveedorId): Promise<Proveedor | null>;
    update(proveedor: Proveedor): Promise<Proveedor>
    delete(proveedorId: ProveedorId): Promise<void>;
}