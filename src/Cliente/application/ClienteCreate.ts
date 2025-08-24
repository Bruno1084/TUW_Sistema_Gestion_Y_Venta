import { Cliente } from "../domain/Cliente";
import type { ClienteRepository } from "../domain/ClienteRepository";
import { ClienteId } from "../domain/ClienteId";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteFechaCreacion } from "../domain/ClienteFechaCreacion";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";
import { ClienteEsActivo } from "../domain/ClienteEsActivo";

export class ClienteCreate {
    constructor(private repository: ClienteRepository) {}

    async run(
        id: number,
        nombre: string,
        direccion: string,
        telefono: string,
        fechaCreacion: Date,
        fechaModificacion: Date,
        esActivo: boolean
    ): Promise<void> {
        const cliente = new Cliente(
            new ClienteId(id),
            new ClienteNombre(nombre),
            new ClienteDireccion(direccion),
            new ClienteTelefono(telefono),
            new ClienteFechaCreacion(fechaCreacion),
            new ClienteFechaModificacion(fechaModificacion),
            new ClienteEsActivo(true)
        );

        await this.repository.create(cliente);
    }
}