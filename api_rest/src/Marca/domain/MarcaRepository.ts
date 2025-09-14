import type { Marca } from "./Marca";
import type { MarcaId } from "./MarcaId";

export interface MarcaRepository {
    create(marca: Marca): Promise<Marca>;
    getAll(): Promise<Marca []>;
    getOneById(marcaId: MarcaId): Promise<Marca | null>;
    update(marca: Marca): Promise<Marca>;
    delete(marcaId: MarcaId): Promise<void>;
}