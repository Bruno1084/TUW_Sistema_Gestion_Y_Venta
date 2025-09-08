import { ClienteId } from "../domain/ClienteId";
import type { ClienteRepository } from "../domain/ClienteRepository";

export class ClienteGetOneById {
    constructor(private repository: ClienteRepository) {}

    async run(id: number) {
        const cliente = await this.repository.getOneById(new ClienteId(id));

        if(!cliente) {
            throw new Error('Cliente no encontrado');
        }

        return cliente;
    }
}