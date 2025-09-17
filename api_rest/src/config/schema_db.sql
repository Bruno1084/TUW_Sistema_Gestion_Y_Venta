create schema gestion_elcacho_db;
use gestion_elcacho_db;

create table empleados(
	id int not null auto_increment,
    nombre varchar(80) not null,
    direccion varchar(255) not null,
    telefono varchar(15) not null,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    es_activo bool not null default true,
    primary key(id)
);

create table repartidores(
	id_empleado int not null,
    primary key(id_empleado),
    foreign key(id_empleado) references empleados(id)
);

create table cajeros(
	id_empleado int not null,
    contrasenia_hash VARCHAR(128) NOT NULL,
    contrasenia_salt VARCHAR(32) NOT NULL,
    primary key(id_empleado),
    foreign key(id_empleado) references empleados(id)
);

create table clientes(
	id int not null auto_increment,
    nombre varchar(80) not null,
    direccion varchar(255),
	telefono varchar(15) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	es_activo bool not null default true,
    primary key(id)
);

create table proveedores(
	id int not null auto_increment,
    nombre varchar(80) not null,
    direccion varchar(255) not null,
    telefono varchar(15) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	es_activo bool not null default true,
    primary key(id)
);

create table marcas(
	id int not null auto_increment,
    nombre varchar(50),
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key(id)
);

create table rubros(
	id int not null auto_increment,
    nombre varchar(50),
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    id_empleado int not null,
    precio_total decimal(10, 2) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id),
    foreign key(id_cliente) references clientes(id),
    foreign key(id_empleado) references empleados(id)
);

create table ventas_detalles(
	id_venta int not null,
    codigo_producto varchar(50) not null,
    cantidad float not null,
    descuento float not null,
    precio_total decimal(10, 2) not null,
	precio_unitario decimal(10, 2) not null,
    primary key(id_venta, codigo_producto),
    foreign key(codigo_producto) references productos(codigo_barra),
    foreign key(id_venta) references ventas(id)
);

create table compras(
	id int not null auto_increment,
    id_proveedor int not null,
    id_empleado int not null,
    precio_total decimal(10, 2) not null,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id),
    foreign key(id_proveedor) references proveedores(id),
    foreign key(id_empleado) references empleados(id)
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