import type { Cliente } from "./Cliente";
import type { ClienteId } from "./ClienteId";

export interface ClienteRepository {
    create(cliente: Cliente): Promise<void>;
    getAll(): Promise<Cliente[]>;
    getOneById(clienteId: ClienteId): Promise<Cliente | null>;
    update(cliente: Cliente): Promise<void>;
    delete(clienteId: ClienteId): Promise<void>;
}