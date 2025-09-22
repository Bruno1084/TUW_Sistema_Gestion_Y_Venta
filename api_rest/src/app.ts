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
import { initCajeroModule } from "./Cajero/initCajeroModule";
import { initVentaModule } from "./Venta/initVentaModule";
import { initUsuarioModule } from "./Usuario/initUsuarioModule";
import { authMiddleware } from "./middlewares/authMiddleware";

const app = express();
const pool = createPoolMySQL();

// Middlewares
app.use(authMiddleware);
app.use(express.json());


// Dependencias
const proveedorRepo = new MySQLProveedorRepository(pool);
const marcaRepo = new MySQLMarcaRepository(pool);
const rubroRepo = new MySQLRubroRepository(pool);
// const empleadoRepo = new MySQLEmpleadoRepository(pool);

// Routers
app.use('/api/usuarios', initUsuarioModule(pool));
app.use('/api/cajeros', initCajeroModule(pool));
app.use('/api/clientes', initClienteModule(pool));
app.use('/api/empleados', initEmpleadoModule(pool));
app.use('/api/marcas', initMarcaModule(pool));
app.use('/api/proveedores', initProveedorModule(pool));
app.use('/api/rubros', initRubroModule(pool));
app.use('/api/productos', initProductoModule(pool, proveedorRepo, marcaRepo, rubroRepo));
app.use('/api/compras', initCompraModule(pool));
app.use('/api/ventas', initVentaModule(pool));

// Ping test
app.get("/api/ping", (req: Request, res: Response) => {
    res.status(200).send("Pong");
});

app.listen(8080, () => {
    console.log("✅ Server running at http://localhost:8080");
}).on("error", (error) => {
    throw new Error(error.message);
});
