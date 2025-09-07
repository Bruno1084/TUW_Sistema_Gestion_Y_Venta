import express, { type Request, type Response } from "express";
import { createPoolMySQL } from "./pool";
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
import { ProveedorController } from "./Proveedor/interfaces/ProveedorController";
import { MySQLClienteRepository } from "./Cliente/infrastructure/MySQLClienteRepository";
import { ClienteCreate } from "./Cliente/application/ClienteCreate";
import { ClienteGetAll } from "./Cliente/application/ClienteGetAll";
import { ClienteGetOneById } from "./Cliente/application/ClienteGetOneById";
import { ClienteUpdate } from "./Cliente/application/ClienteUpdate";
import { ClienteDelete } from "./Cliente/application/ClienteDelete";
import { ClienteController } from "./Cliente/interfaces/ClienteController";
import { clienteRouter } from "./Cliente/interfaces/ClienteRouter";
import { proveedorRouter } from "./Proveedor/interfaces/ProveedorRouter";
import { MySQLMarcaRepository } from "./Marca/infrastructure/MySQLMarcaRepository";
import { MarcaCreate } from "./Marca/application/MarcaCreate";
import { MarcaGetAll } from "./Marca/application/MarcaGetAll";
import { MarcaGetOneById } from "./Marca/application/MarcaGetOneById";
import { MarcaUpdate } from "./Marca/application/MarcaUpdate";
import { MarcaDelete } from "./Marca/application/MarcaDelete";
import { MarcaController } from "./Marca/interfaces/MarcaController";
import { marcaRouter } from "./Marca/interfaces/MarcaRouter";

const app = express();
app.use(express.json());
const pool = createPoolMySQL();

// Repositorio Marca
const marcaRepo = new MySQLMarcaRepository(pool);

const marcaUseCases = {
    create: new MarcaCreate(marcaRepo),
    getAll: new MarcaGetAll(marcaRepo),
    getOneById: new MarcaGetOneById(marcaRepo),
    update: new MarcaUpdate(marcaRepo),
    delete: new MarcaDelete(marcaRepo)
};

const marcaController = new MarcaController(marcaUseCases);

// Repositorio Cliente
const clienteRepo = new MySQLClienteRepository(pool);

const clienteUseCases = {
    create: new ClienteCreate(clienteRepo),
    getAll: new ClienteGetAll(clienteRepo),
    getOneById: new ClienteGetOneById(clienteRepo),
    update: new ClienteUpdate(clienteRepo),
    delete: new ClienteDelete(clienteRepo)
};

const clienteController = new ClienteController(clienteUseCases);

// Repositorio Proveedor
const proveedorRepo = new MySQLProveedorRepository(pool);

const proveedorUseCases = {
    create: new ProveedorCreate(proveedorRepo),
    getAll: new ProveedorGetAll(proveedorRepo),
    getOneById: new ProveedorGetOneById(proveedorRepo),
    update: new ProveedorUpdate(proveedorRepo),
    delete: new ProveedorDelete(proveedorRepo)
};

const proveedorController = new ProveedorController(proveedorUseCases);

// Repositorio Producto
const productoRepo = new MySQLProductoRepository(pool);

const productoUseCases = {
    create: new ProductoCreate(productoRepo),
    getAll: new ProductoGetAll(productoRepo),
    getOneById: new ProductoGetOneById(productoRepo),
    update: new ProductoUpdate(productoRepo, proveedorRepo, marcaRepo, rubroRepo),
};

const productoController = new ProductoController(productoUseCases);

// Routes - Marca
app.use("/marcas", marcaRouter(marcaController));

// Routes - Empleado
// app.use("/empleados", empleadoRouter);

// Router - Cliente
app.use("/clientes", clienteRouter(clienteController));

// Router - Proveedor
app.use("/proveedor", proveedorRouter(proveedorController));

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
