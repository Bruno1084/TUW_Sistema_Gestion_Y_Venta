import type { ClienteId } from "../domain/ClienteId";
import type { ClienteRepository } from "../domain/ClienteRepository";

export class ClienteGetOneById {
    constructor(private repository: ClienteRepository) {}

    async run(clienteId: ClienteId) {
        const cliente = await this.repository.getOneById(clienteId);

        if(!cliente) {
            throw new Error('Cliente no encontrado');
        }

        return cliente;
    }
}