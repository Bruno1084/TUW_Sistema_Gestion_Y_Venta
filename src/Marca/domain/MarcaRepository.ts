import type { Marca } from "./Marca";
import type { MarcaId } from "./MarcaId";

export interface MarcaRepository {
    create(marca: Marca): Promise<void>;
    getAll(): Promise<Marca []>;
    getOneById(marcaId: MarcaId): Promise<Marca | null>;
    update(marca: Marca): Promise<void>;
    delete(marcaId: MarcaId): Promise<void>;
}