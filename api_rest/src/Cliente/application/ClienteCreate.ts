import type { ClienteRepository } from "../domain/ClienteRepository";
import type { ClienteDTO } from "./ClienteDTO";
import { Cliente } from "../domain/Cliente";
import { ClienteId } from "../domain/ClienteId";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteFechaCreacion } from "../domain/ClienteFechaCreacion";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";

export class ClienteCreate {
    constructor(private repository: ClienteRepository) {}

    async run(
        nombre: string,
        direccion: string,
        telefono: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
    ): Promise<ClienteDTO> {
        const cliente = new Cliente(
            new ClienteId(0),
            new ClienteNombre(nombre),
            new ClienteDireccion(direccion),
            new ClienteTelefono(telefono),
            new ClienteFechaCreacion(fechaCreacion),
            new ClienteFechaModificacion(fechaModificacion),
        );

        return await this.repository.create(cliente);
    }
}