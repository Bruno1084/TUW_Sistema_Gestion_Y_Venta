import type { RubroDTO } from "../application/RubroDTO";
import type { Rubro } from "./Rubro";
import type { RubroId } from "./RubroId";

export interface RubroRepository {
    create(rubro: Rubro): Promise<RubroDTO>;
    getAll(): Promise<RubroDTO[]>;
    getOneById(rubroId: RubroId): Promise<RubroDTO | null>;
    update(rubro: Rubro): Promise<RubroDTO>;
    delete(rubroId: RubroId): Promise<void>;
}