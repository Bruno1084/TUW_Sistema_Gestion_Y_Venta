import type { ClienteRepository } from "../domain/ClienteRepository";
import { ClienteId } from "../domain/ClienteId";

export class ClienteDelete {
    constructor(private repository: ClienteRepository) {}

    async run(clienteId: number): Promise<void> {
        return await this.repository.delete(new ClienteId(clienteId));
    }
}