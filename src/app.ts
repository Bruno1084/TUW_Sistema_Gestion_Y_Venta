import express, { type Request, type Response } from "express";
import { createPoolMySQL } from "./pool";
import { empleadoRouter } from "./Empleado/interfaces/EmpleadoRouter";
import { productoRouter } from "./Producto/interfaces/ProductoRouter";
import { ProductoCreate } from "./Producto/application/ProductoCreate";
import { ProductoGetAll } from "./Producto/application/ProductoGetAll";
import { ProductoGetOneById } from "./Producto/application/ProductoGetOneById";
import { ProductoUpdate } from "./Producto/application/ProductoUpdate";
import { ProductoController } from "./Producto/interfaces/ProductoController";
import { MySQLProductoRepository } from "./Producto/infrastructure/MySQLProductoRepository";
import { MySQLProveedorRepository } from "./Proveedor/infrastructure/MySQLProveedorRepository";
import { ProveedorCreate } from "./Proveedor/application/ProveedorCreate";
import { ProveedorGetAll } from "./Proveedor/application/ProveedorGetAll";
import { ProveedorGetOneById } from "./Proveedor/application/ProveedorGetOneById";
import { ProveedorUpdate } from "./Proveedor/application/ProveedorUpdate";
import { ProveedorDelete } from "./Proveedor/application/ProveedorDelete";

const app = express();
app.use(express.json());
const pool = createPoolMySQL();

// Repositorio Proveedor
const proveedorRepo = new MySQLProveedorRepository(pool);

const proveedorUseCases = {
    create: new ProveedorCreate(proveedorRepo),
    getAll: new ProveedorGetAll(proveedorRepo),
    getOneById: new ProveedorGetOneById(proveedorRepo),
    update: new ProveedorUpdate(proveedorRepo),
    delete: new ProveedorDelete(proveedorRepo)
};

const proveedorController = new ProveedorController(proveedorUseCases)

// Repositorio Producto
const productoRepo = new MySQLProductoRepository(pool);

const productoUseCases = {
    create: new ProductoCreate(productoRepo),
    getAll: new ProductoGetAll(productoRepo),
    getOneById: new ProductoGetOneById(productoRepo),
    update: new ProductoUpdate(productoRepo, proveedorRepo, marcaRepo, rubroRepo),
};

const productoController = new ProductoController(productoUseCases);

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
