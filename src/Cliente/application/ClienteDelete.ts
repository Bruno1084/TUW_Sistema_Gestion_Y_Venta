import { ClienteId } from "../domain/ClienteId";
import type { ClienteRepository } from "../domain/ClienteRepository";

export class ClienteDelete {
    constructor(private repository: ClienteRepository) {}

    async run(clienteId: number): Promise<void> {
        await this.repository.delete(new ClienteId(clienteId));
    }
}