import express, { type Request, type Response } from "express";
import { createPoolMySQL } from "./pool";
import { initClienteModule } from "./Cliente/initClienteModule";
import { initEmpleadoModule } from "./Empleado/initEmpleadoModule";
import { initMarcaModule } from "./Marca/initMarcaModule";
import { initProductoModule } from "./Producto/initProductoModule";
import { initProveedorModule } from "./Proveedor/initProveedorModule";
import { initCompraModule } from "./Compra/initCompraModule";
import { initRubroModule } from "./Rubro/initRubroModule";
import { MySQLProveedorRepository } from "./Proveedor/infrastructure/MySQLProveedorRepository";
import { MySQLMarcaRepository } from "./Marca/infrastructure/MySQLMarcaRepository";
import { MySQLRubroRepository } from "./Rubro/infrastructure/MySQLRubroRepository";
import { MySQLEmpleadoRepository } from "./Empleado/infrastructure/MySQLEmpleadoRepository";
import { initCajeroModule } from "./Cajero/initCajeroModule";
import { initAuthModule } from "./Auth/initAuthModule";

const app = express();
app.use(express.json());
const pool = createPoolMySQL();

// Dependencias
const proveedorRepo = new MySQLProveedorRepository(pool);
const marcaRepo = new MySQLMarcaRepository(pool);
const rubroRepo = new MySQLRubroRepository(pool);
const empleadoRepo = new MySQLEmpleadoRepository(pool);

// Routers
app.use('/auth', initAuthModule(pool));
app.use('/clientes', initClienteModule(pool));
app.use('/empleados', initEmpleadoModule(pool));
app.use('/cajeros', initCajeroModule(pool));
app.use('/marcas', initMarcaModule(pool));
app.use('/proveedores', initProveedorModule(pool));
app.use('/rubros', initRubroModule(pool));
app.use('/productos', initProductoModule(pool, proveedorRepo, marcaRepo, rubroRepo));
app.use('/compras', initCompraModule(pool));
// app.use('/ventas', initVentaModule(pool));


// Ping test
app.get("/ping", (req: Request, res: Response) => {
    res.status(200).send("pong");
});

app.listen(8080, () => {
    console.log("✅ Server running at http://localhost:8080");
}).on("error", (error) => {
    throw new Error(error.message);
});
