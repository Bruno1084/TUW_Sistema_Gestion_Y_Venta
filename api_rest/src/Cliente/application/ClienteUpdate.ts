import type { ClienteRepository } from "../domain/ClienteRepository";
import { Cliente } from "../domain/Cliente";
import { ClienteNombre } from "../domain/ClienteNombre";
import { ClienteDireccion } from "../domain/ClienteDireccion";
import { ClienteTelefono } from "../domain/ClienteTelefono";
import { ClienteId } from "../domain/ClienteId";
import { ClienteFechaModificacion } from "../domain/ClienteFechaModificacion";
import { ClienteEsActivo } from "../domain/ClienteEsActivo";

export class ClienteUpdate {
    constructor(private repository: ClienteRepository) { }

    async run(
        id: number,
        updates: {
            nombre?: string,
            direccion?: string,
            telefono?: string,
        }
    ): Promise<Cliente> {
        const clienteExistente = await this.repository.getOneById(new ClienteId(id));
        if (!clienteExistente) throw new Error("Producto no encontrado");

        const clienteActualizado = new Cliente(
            new ClienteId(id),
            updates.nombre ? new ClienteNombre(updates.nombre) : clienteExistente.nombre,
            updates.direccion ? new ClienteDireccion(updates.direccion) : clienteExistente.direccion,
            updates.telefono ? new ClienteTelefono(updates.telefono) : clienteExistente.telefono,
            clienteExistente.fechaCreacion,
            new ClienteFechaModificacion(new Date()),
            new ClienteEsActivo(clienteExistente.esActivo.value),
        );

        return await this.repository.update(clienteActualizado);
    }
}