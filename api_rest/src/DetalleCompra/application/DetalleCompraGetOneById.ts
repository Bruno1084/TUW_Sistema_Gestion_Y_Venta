import type { DetalleCompraRepository } from "../domain/DetalleCompraRepository";
import type { DetalleCompraDTO } from "./DetalleCompraDTO";
import { CompraId } from "../../Compra/domain/CompraId";
import { ProductoCodigoBarra } from "../../Producto/domain/ProductoCodigoBarra";


export class DetalleCompraGetOneById {
    constructor(private repository: DetalleCompraRepository) { }

    async run(compraId: number, productoCodigoBarra: string): Promise<DetalleCompraDTO> {
        const detalleCompra = await this.repository.getOneById(new CompraId(compraId), new ProductoCodigoBarra(productoCodigoBarra));
        if (!detalleCompra) throw new Error('Detalle Compra no encontrada');

        return detalleCompra;
    }
}