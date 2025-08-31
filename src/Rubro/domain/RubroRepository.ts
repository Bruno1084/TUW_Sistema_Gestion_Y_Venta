import type { Rubro } from "./Rubro";
import type { RubroId } from "./RubroId";

export interface RubroRepository {
    create(rubro: Rubro): Promise<void>;
    getAll(): Promise<Rubro[]>;
    getOneById(rubroId: RubroId): Promise<Rubro | null>;
    update(rubro: Rubro): Promise<void>;
    delete(rubroId: RubroId): Promise<null>;
}