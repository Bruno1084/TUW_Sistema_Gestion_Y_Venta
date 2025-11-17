import express, { type Request, type Response } from "express";
import { createPoolMySQL } from "./pool";
import { MySQLProveedorRepository } from "./Proveedor/infrastructure/MySQLProveedorRepository";
import { MySQLMarcaRepository } from "./Marca/infrastructure/MySQLMarcaRepository";
import { MySQLRubroRepository } from "./Rubro/infrastructure/MySQLRubroRepository";
import { authMiddleware } from "./middlewares/authMiddleware";
import { compraRouter } from "./Compra/interfaces/CompraRouter";
import { ventaRouter } from "./Venta/interfaces/VentaRouter";
import { clienteRouter } from "./Cliente/interfaces/ClienteRouter";
import { empleadoRouter } from "./Empleado/interfaces/EmpleadoRouter";
import { marcaRouter } from "./Marca/interfaces/MarcaRouter";
import { proveedorRouter } from "./Proveedor/interfaces/ProveedorRouter";
import { rubroRouter } from "./Rubro/interfaces/RubroRouter";
import { productoRouter } from "./Producto/interfaces/ProductoRouter";
import { usuarioRouter } from "./Usuario/interfaces/UsuarioRouter";

const app = express();
const pool = createPoolMySQL();

// Middlewares
app.use(express.json());
app.use(authMiddleware);


// Dependencias
const proveedorRepo = new MySQLProveedorRepository(pool);
const marcaRepo = new MySQLMarcaRepository(pool);
const rubroRepo = new MySQLRubroRepository(pool);

// Routers
app.use('/api', usuarioRouter(pool));
app.use('/api', clienteRouter(pool));
app.use('/api', empleadoRouter(pool));
app.use('/api', marcaRouter(pool));
app.use('/api', proveedorRouter(pool));
app.use('/api', rubroRouter(pool));
app.use('/api', productoRouter(pool, proveedorRepo, marcaRepo, rubroRepo));
app.use('/api', compraRouter(pool));
app.use('/api', ventaRouter(pool));

// Ping test
app.get("/api/ping", (req: Request, res: Response) => {
    res.status(200).send("Pong");
});

app.listen(8080, () => {
    console.log("✅ Server running at http://localhost:8080");
}).on("error", (error) => {
    throw new Error(error.message);
});
