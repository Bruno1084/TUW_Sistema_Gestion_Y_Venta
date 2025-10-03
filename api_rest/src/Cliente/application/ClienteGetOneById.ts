import type { ClienteRepository } from "../domain/ClienteRepository";
import type { ClienteDTO } from "./ClienteDTO";
import { ClienteId } from "../domain/ClienteId";

export class ClienteGetOneById {
    constructor(private repository: ClienteRepository) {}

    async run(id: number): Promise<ClienteDTO> {
        const cliente = await this.repository.getOneById(new ClienteId(id));

        if(!cliente) {
            throw new Error('Cliente no encontrado');
        }

        return cliente;
    }
}