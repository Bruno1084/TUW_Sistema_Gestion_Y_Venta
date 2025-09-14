import type { Cliente } from "./Cliente";
import type { ClienteId } from "./ClienteId";

export interface ClienteRepository {
    create(cliente: Cliente): Promise<Cliente>;
    getAll(): Promise<Cliente[]>;
    getOneById(clienteId: ClienteId): Promise<Cliente | null>;
    update(cliente: Cliente): Promise<Cliente>;
    delete(clienteId: ClienteId): Promise<void>;
}