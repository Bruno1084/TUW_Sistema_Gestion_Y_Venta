import type { Cliente } from "../domain/Cliente";
import type { ClienteRepository } from "../domain/ClienteRepository";

export class ClienteGetAll {
    constructor(private repository: ClienteRepository) {}

    async run(): Promise<Cliente[]> {
        return await this.repository.getAll();
    }
}