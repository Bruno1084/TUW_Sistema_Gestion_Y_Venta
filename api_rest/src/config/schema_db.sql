create schema gestion_elcacho_db;
use gestion_elcacho_db;

create table usuarios(
    id int not null auto_increment,
    nombre varchar(80) not null,
    contrasenia_hash VARCHAR(128) NOT NULL,
    contrasenia_salt VARCHAR(32) NOT NULL,
    primary key(id)
);

create table empleados(
	id int not null auto_increment,
    nombre varchar(80) not null,
    direccion varchar(255),
    telefono varchar(15) not null,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    es_activo bool not null default true,
    primary key(id)
);

create table clientes(
	id int not null auto_increment,
    nombre varchar(80) not null,
    direccion varchar(255),
	telefono varchar(15),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	es_activo bool not null default true,
    primary key(id)
);

create table proveedores(
	id int not null auto_increment,
    nombre varchar(80) unique not null,
    direccion varchar(255),
    telefono varchar(15),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	es_activo bool not null default true,
    primary key(id)
);

create table marcas(
	id int not null auto_increment,
    nombre varchar(50) unique not null ,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    es_activo bool not null default true,
    primary key(id)
);

create table rubros(
	id int not null auto_increment,
    nombre varchar(50) unique not null,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    es_activo bool not null default true,
    primary key(id)
);

create table productos(
	codigo_barra varchar(50) not null,
    id_proveedor int not null,
    descripcion varchar(255) not null,
    id_marca int not null,
    id_rubro int not null,
    precio_compra decimal(10, 2),
    precio_venta decimal(10, 2) not null,
    stock int not null,
	img_uri varchar(255),
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    es_activo bool not null default true,
    primary key(codigo_barra),
    foreign key(id_proveedor) references proveedores(id),
    foreign key(id_marca) references marcas(id),
    foreign key(id_rubro) references rubros(id)
);

create table ventas(
	id int not null auto_increment,
    id_cliente int not null,
    id_usuario int not null,
    precio_total decimal(10, 2) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id),
    foreign key(id_cliente) references clientes(id),
    foreign key(id_usuario) references usuarios(id)
);

create table ventas_detalles(
	id_venta int not null,
    codigo_producto varchar(50) not null,
    cantidad float not null,
    precio_total decimal(10, 2) not null,
	precio_unitario decimal(10, 2) not null,
    primary key(id_venta, codigo_producto),
    foreign key(codigo_producto) references productos(codigo_barra),
    foreign key(id_venta) references ventas(id)
);

create table compras(
	id int not null auto_increment,
    id_proveedor int not null,
    id_usuario int not null,
    precio_total decimal(10, 2) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id),
    foreign key(id_proveedor) references proveedores(id),
    foreign key(id_usuario) references usuarios(id)
);

create table compras_detalles(
	id_compra int not null,
    codigo_producto varchar(50) not null,
    cantidad float not null,
    precio_total decimal(10, 2) not null,
    precio_unitario decimal(10, 2) not null,
    primary key(id_compra, codigo_producto),
    foreign key(id_compra) references compras(id),
    foreign key(codigo_producto) references productos(codigo_barra)
);


INSERT INTO usuarios (nombre, contrasenia_hash, contrasenia_salt) VALUES
('admin', 'hash123', 'salt123'),
('vendedor1', 'hashABC', 'saltABC');

INSERT INTO empleados (nombre, direccion, telefono) VALUES
('Carlos Gómez', 'Av. Siempreviva 123', '266400001'),
('Lucía Martínez', 'Calle Falsa 742', '266400002');

INSERT INTO clientes (nombre, direccion, telefono) VALUES
('Juan Pérez', 'Belgrano 456', '266450001'),
('María López', 'San Martín 890', '266450002'),
('Comercio El Sol', 'Av. Illia 1234', '266450003');

INSERT INTO proveedores (nombre, direccion, telefono) VALUES
('Distribuidora Central', 'Ruta 3 Km 12', '266410001'),
('Alimentos San Luis', 'Parque Industrial Sur', '266410002'),
('TecnoPro', 'Av. Industrial 1500', '266410003');

INSERT INTO marcas (nombre) VALUES
('Arcor'),
('Samsung'),
('Lenovo'),
('Coca-Cola');

INSERT INTO rubros (nombre) VALUES
('Golosinas'),
('Electrónica'),
('Bebidas'),
('Limpieza');

INSERT INTO productos (
    codigo_barra, id_proveedor, descripcion, id_marca, id_rubro,
    precio_compra, precio_venta, stock, img_uri
) VALUES
('1001001001', 1, 'Chocolate Arcor 50g', 1, 1, 150.00, 250.00, 120, 'img/choco_arcor.png'),
('2002002002', 3, 'Smartphone Samsung A04', 2, 2, 85000.00, 105000.00, 15, 'img/samsung_a04.png'),
('3003003003', 3, 'Notebook Lenovo V14', 3, 2, 230000.00, 270000.00, 8, 'img/lenovo_v14.png'),
('4004004004', 2, 'Coca-Cola 1.5L', 4, 3, 450.00, 650.00, 60, 'img/coca_15.png'),
('5005005005', 1, 'Detergente Limol 900ml', 1, 4, 300.00, 450.00, 40, 'img/limol.png');

INSERT INTO ventas (id_cliente, id_usuario, precio_total) VALUES
(1, 1, 900.00),
(2, 2, 105000.00),
(3, 1, 270650.00);

-- Venta 1
INSERT INTO ventas_detalles VALUES
(1, '1001001001', 2, 500.00, 250.00),
(1, '4004004004', 1, 400.00, 400.00);

-- Venta 2
INSERT INTO ventas_detalles VALUES
(2, '2002002002', 1, 105000.00, 105000.00);

-- Venta 3
INSERT INTO ventas_detalles VALUES
(3, '3003003003', 1, 270000.00, 270000.00),
(3, '4004004004', 1, 650.00, 650.00);

INSERT INTO compras (id_proveedor, id_usuario, precio_total) VALUES
(1, 1, 2500.00),
(3, 2, 315000.00);

-- Compra 1: Reposición de golosinas
INSERT INTO compras_detalles VALUES
(1, '1001001001', 10, 1500.00, 150.00),
(1, '5005005005', 5, 1000.00, 200.00);

-- Compra 2: Stock de electrónica
INSERT INTO compras_detalles VALUES
(2, '2002002002', 3, 255000.00, 85000.00),
(2, '3003003003', 1, 60000.00, 60000.00);
