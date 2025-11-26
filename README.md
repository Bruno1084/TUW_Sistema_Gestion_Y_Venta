# SISTEMA GESTIÓN Y VENTA
Sistema integral para la gestión de productos, compras y ventas, desarrollado como proyecto académico/profesional.

## 🚀 Tecnologías
### Frontend
* Java JDK 20.0.2
* JavaFX 24.0.1
* Scene Builder 24.0.1

### Backend
* Nodejs 22.12.0
* Typescript 5.9.2
* Express 5.1.0
* MySQL 

## 📌 Requisitos previos

Antes de instalar el proyecto, asegurate de tener:

* Node.js 18+
* MySQL Server
* Java JDK 20+
* Maven / JavaFX SDK

## ⚙️ Instalación
1. Configurar la Base de Datos

Ejecutar el script ubicado en:
```
api_rest/src/config/schema_db.sql
```
Esto creará la base de datos y las tablas necesarias para el funcionamiento del sistema.

2. Iniciar el Backend

Dentro de la carpeta **api_rest**
```
npm install
npm run dev
```
El servidor se iniciará en el puerto por defecto: http://localhost:8080

3. Iniciar la Aplicación JavaFX

Abrir el proyecto en tu IDE (IntelliJ, Eclipse o NetBeans) y ejecutar la clase principal del módulo JavaFX.

## 🧱 Arquitectura del Proyecto
```
TUW_Sistema_Gestion_Y_Venta/
│
├── api_rest/                       # Backend Node.js + Express
│   ├── src/
│   │   ├── config/                 # Configuración y schema SQL
│   │   ├── Cliente/    
│   │   ├── Compra/         
│   │   ├── DetalleCompra/ 
│   │   ├── DetalleVenta/
│   │   ├── Empleado/
│   │   ├── Marca/
│   │   ├── middlewares/
│   │   ├── Producto/
│   │   ├── Proveedor/
│   │   ├── Rubro/
│   │   ├── Usuario/
│   │   ├── app.ts                  # Configuración principal de Express
│   │   ├── pool.ts                 # Pool de coneccion a la base de datos
│   └── package.json
│
└── desktop_app/                    # Cliente de escritorio en JavaFX
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   ├── controller/
    │   │   │   ├── model/
    │   │   │   ├── service/
    │   │   │   ├── util/
    │   │   │   └── Main            # Clase principal
    │   │   └── module-info.java
    │   └── resources/
    │       ├── css/
    │       └── fxml/
    └ pom.fxml
```
