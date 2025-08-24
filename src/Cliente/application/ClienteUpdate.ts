import type { ClienteRepository } from "../domain/ClienteRepository";
import { Cliente } from "../domain/Cliente";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteId } from "../domain/ClienteId";
import { ClienteFechaCreacion } from "../domain/ClienteFechaCreacion";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";
import { ClienteEsActivo } from "../domain/ClienteEsActivo";

export class ClienteUpdate {
    constructor(private repository: ClienteRepository) {}

    async run(
        nombre: string,
        direccion: string,
        telefono: string,        
    ): Promise<void> {
        const cliente = new Cliente(
            new ClienteId(0),
            new ClienteNombre(nombre),
            new ClienteDireccion(direccion),
            new ClienteTelefono(telefono),
            new ClienteFechaCreacion(new Date()),
            new ClienteFechaModificacion(new Date()),
            new ClienteEsActivo(true)
        );

        await this.repository.update(cliente);
    }
}