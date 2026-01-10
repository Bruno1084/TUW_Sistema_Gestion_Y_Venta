import type { ClienteDTO } from "../application/ClienteDTO";
import type { Cliente } from "./Cliente";
import type { ClienteId } from "./ClienteId";

export interface ClienteRepository {
    create(cliente: Cliente): Promise<ClienteDTO>;
    getAll(): Promise<ClienteDTO[]>;
    getOneById(clienteId: ClienteId): Promise<ClienteDTO | null>;
    update(cliente: Cliente): Promise<ClienteDTO>;
    delete(clienteId: ClienteId): Promise<void>;
}