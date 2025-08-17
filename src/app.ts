import express, { type Request, type Response } from "express";
import { empleadoRouter } from "./Empleado/interfaces/EmpleadoRouter";

const app = express();
app.use(express.json());

// Routes - Empleado
app.use("/empleados", empleadoRouter);

// Routes - Producto
// app.use("/productos", productoRouter);

// Routes - Cajero
// app.use("/cajeros", cajeroRouter);


// Ping test
app.get("/ping", (req: Request, res: Response) => {
    res.status(200).send("pong");
});

app.listen(8080, () => {
    console.log("✅ Server running at http://localhost:8080");
}).on("error", (error) => {
    throw new Error(error.message);
});
