import type { ClienteRepository } from "../domain/ClienteRepository";
import type { ClienteDTO } from "./ClienteDTO";

export class ClienteGetAll {
    constructor(private repository: ClienteRepository) {}

    async run(): Promise<ClienteDTO[]> {
        return await this.repository.getAll();
    }
}