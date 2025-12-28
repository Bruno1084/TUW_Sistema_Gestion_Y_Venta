import type { MarcaDTO, MarcaSimpleDTO } from "../application/MarcaDTO";
import type { Marca } from "./Marca";
import type { MarcaId } from "./MarcaId";

export interface MarcaRepository {
    create(marca: Marca): Promise<MarcaDTO>;
    getAll(): Promise<MarcaDTO []>;
    getOneById(marcaId: MarcaId): Promise<MarcaDTO | null>;
    update(marca: Marca): Promise<MarcaDTO>;
    delete(marcaId: MarcaId): Promise<void>;
    findByNames(nombres: string[]): Promise<MarcaSimpleDTO[]>;
}