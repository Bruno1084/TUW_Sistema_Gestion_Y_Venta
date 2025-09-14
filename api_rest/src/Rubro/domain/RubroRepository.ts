import type { Rubro } from "./Rubro";
import type { RubroId } from "./RubroId";

export interface RubroRepository {
    create(rubro: Rubro): Promise<Rubro>;
    getAll(): Promise<Rubro[]>;
    getOneById(rubroId: RubroId): Promise<Rubro | null>;
    update(rubro: Rubro): Promise<Rubro>;
    delete(rubroId: RubroId): Promise<void>;
}