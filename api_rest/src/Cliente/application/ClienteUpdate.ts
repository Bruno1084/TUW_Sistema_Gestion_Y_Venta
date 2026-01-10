import type { ClienteRepository } from "../domain/ClienteRepository";
import type { ClienteDTO } from "./ClienteDTO";
import { Cliente } from "../domain/Cliente";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteId } from "../domain/ClienteId";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";
import { ClienteFechaCreacion } from "../domain/ClienteFechaCreacion";

export class ClienteUpdate {
    constructor(private repository: ClienteRepository) { }

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string,
        }
    ): Promise<ClienteDTO> {
        const clienteExistente = await this.repository.getOneById(new ClienteId(id));
        if (!clienteExistente) throw new Error("Producto no encontrado");

        const clienteActualizado = new Cliente(
            new ClienteId(id),
            updates.nombre ? new ClienteNombre(updates.nombre) : new ClienteNombre(clienteExistente.nombre),
            updates.direccion ? new ClienteDireccion(updates.direccion) : new ClienteDireccion(clienteExistente.direccion),
            updates.telefono ? new ClienteTelefono(updates.telefono) : new ClienteTelefono(clienteExistente.telefono),
            new ClienteFechaCreacion(clienteExistente.fechaCreacion),
            new ClienteFechaModificacion(new Date())
        );

        return await this.repository.update(clienteActualizado);
    }
}