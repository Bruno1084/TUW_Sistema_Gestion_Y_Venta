import express, { type Request, type Response } from "express";
import { empleadoRouter } from "./Empleado/interfaces/EmpleadoRouter";
import { productoRouter } from "./Producto/interfaces/ProductoRouter";
import { MySQLProductoRepository } from "./Producto/infrastructure/MySQLProductoRepository";
import { ProductoCreate } from "./Producto/application/ProductoCreate";
import { ProductoGetAll } from "./Producto/application/ProductoGetAll";
import { ProductoGetOneById } from "./Producto/application/ProductoGetOneById";
import { ProductoUpdate } from "./Producto/application/ProductoUpdate";
import { ProductoController } from "./Producto/interfaces/ProductoController";

const app = express();
app.use(express.json());

// Repositorio Producto
const productoRepo = new MySQLProductoRepository();

const useCases = {
    create: new ProductoCreate(productoRepo),
  getAll: new ProductoGetAll(productoRepo),
  getOneById: new ProductoGetOneById(productoRepo),
  update: new ProductoUpdate(productoRepo, proveedorRepo, marcaRepo, rubroRepo),
}

const productoController = new ProductoController(useCases);

// Routes - Empleado
app.use("/empleados", empleadoRouter);

// Router - Producto
app.use("/productos", productoRouter(productoController));




// Ping test
app.get("/ping", (req: Request, res: Response) => {
    res.status(200).send("pong");
});

app.listen(8080, () => {
    console.log("✅ Server running at http://localhost:8080");
}).on("error", (error) => {
    throw new Error(error.message);
});
