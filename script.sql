CREATE DATABASE EPATEC
GO
CREATE LOGIN emmanuel   
    WITH PASSWORD = 'password';  
GO  

USE EPATEC
GO 
-- Creates a database user for the login created above.  
CREATE USER emmanuel FOR LOGIN emmanuel;  
GO  
EXEC sp_addrolemember N'db_datareader', N'emmanuel'
EXEC sp_addrolemember N'db_datawriter', N'emmanuel'

CREATE TABLE CATEGORIA(
	Nombre varchar (50) NOT NULL PRIMARY KEY ,
	Descripción varchar(max))
GO

CREATE TABLE CLIENTE(
	Cedula_Cliente bigint NOT NULL PRIMARY KEY,
	Nombre varchar(max) NOT NULL,
	Apellidos varchar(max) NOT NULL,
	Grado_de_Penalizacion int NULL,
	Lugar_de_Residencia varchar(max),
	Fecha_de_Nacimiento date,
	Telefono varchar(max)
	)

GO
CREATE TABLE CONTIENE (
	Nombre_Producto varchar(30) NOT NULL,
	Id_Pedido bigint NOT NULL,
	Cantidad int
	PRIMARY KEY(Nombre_Producto, Id_Pedido))

GO
CREATE TABLE dbo.EMPLEADO(
	Id_Empleado bigint NOT NULL IDENTITY(1,1) PRIMARY KEY ,
	Id_Sucursal bigint NOT NULL,
	Nombre varchar(max) NOT NULL,
	Puesto varchar(max) NOT NULL
	)

GO
CREATE TABLE dbo.PEDIDO(
	Id_Pedido bigint NOT NULL IDENTITY(1,1) PRIMARY KEY ,
	Cedula_Cliente bigint NOT NULL,
	Id_Sucursal bigint NOT NULL,
	Telefono_Preferido varchar(max) NOT NULL,
	Hora_de_Creación datetime NOT NULL
	)

GO
CREATE TABLE dbo.PRODUCTO(
	Nombre_Producto varchar(30) NOT NULL PRIMARY KEY ,
	Id_Sucursal bigint NOT NULL,
	Cedula_Provedor bigint NOT NULL,
	Nombre_Categoría varchar(50) NOT NULL,
	Descripción varchar(max),
	Exento bit,
	Cantidad_Disponible int NOT NULL,
	Precio int
	)

GO
CREATE TABLE dbo.PROVEEDOR(
	Cedula_Proveedor bigint NOT NULL PRIMARY KEY ,
	Nombre varchar(max) NOT NULL,
	Apellidos varchar(max) NOT NULL,
	Fecha_de_Nacimiento date,
	Lugar_de_Residencia varchar(max)
	)

GO
CREATE TABLE dbo.SUCURSAL(
	Id_Sucursal bigint NOT NULL PRIMARY KEY 
	)

GO
ALTER TABLE dbo.CONTIENE  WITH CHECK ADD  CONSTRAINT FK_CONTIENE_PEDIDO FOREIGN KEY(Id_Pedido)
REFERENCES dbo.PEDIDO (Id_Pedido)
GO
ALTER TABLE dbo.CONTIENE CHECK CONSTRAINT FK_CONTIENE_PEDIDO
GO
ALTER TABLE dbo.CONTIENE  WITH CHECK ADD  CONSTRAINT FK_CONTIENE_PRODUCTO FOREIGN KEY(Nombre_Producto)
REFERENCES dbo.PRODUCTO (Nombre_Producto)
GO
ALTER TABLE dbo.CONTIENE CHECK CONSTRAINT FK_CONTIENE_PRODUCTO
GO
ALTER TABLE dbo.EMPLEADO  WITH CHECK ADD  CONSTRAINT FK_EMPLEADO_SUCURSAL FOREIGN KEY(Id_Sucursal)
REFERENCES dbo.SUCURSAL (Id_Sucursal)
GO
ALTER TABLE dbo.EMPLEADO CHECK CONSTRAINT FK_EMPLEADO_SUCURSAL
GO
ALTER TABLE dbo.PEDIDO  WITH CHECK ADD  CONSTRAINT FK_PEDIDO_CLIENTE FOREIGN KEY(Cedula_Cliente)
REFERENCES dbo.CLIENTE (Cedula_Cliente)
GO
ALTER TABLE dbo.PEDIDO CHECK CONSTRAINT FK_PEDIDO_CLIENTE
GO
ALTER TABLE dbo.PEDIDO  WITH CHECK ADD  CONSTRAINT FK_PEDIDO_SUCURSAL FOREIGN KEY(Id_Sucursal)
REFERENCES dbo.SUCURSAL (Id_Sucursal)
GO
ALTER TABLE dbo.PEDIDO CHECK CONSTRAINT FK_PEDIDO_SUCURSAL
GO
ALTER TABLE dbo.PRODUCTO  WITH CHECK ADD  CONSTRAINT FK_PRODUCTO_CATEGORIA FOREIGN KEY(Nombre_Categoría)
REFERENCES dbo.CATEGORIA (Nombre)
GO
ALTER TABLE dbo.PRODUCTO CHECK CONSTRAINT FK_PRODUCTO_CATEGORIA
GO
ALTER TABLE dbo.PRODUCTO  WITH CHECK ADD  CONSTRAINT FK_PRODUCTO_PROVEEDOR FOREIGN KEY(Cedula_Provedor)
REFERENCES dbo.PROVEEDOR (Cedula_Proveedor)
GO
ALTER TABLE dbo.PRODUCTO CHECK CONSTRAINT FK_PRODUCTO_PROVEEDOR
GO
ALTER TABLE dbo.PRODUCTO  WITH CHECK ADD  CONSTRAINT FK_PRODUCTO_SUCURSAL FOREIGN KEY(Id_Sucursal)
REFERENCES dbo.SUCURSAL (Id_Sucursal)
GO
ALTER TABLE dbo.PRODUCTO CHECK CONSTRAINT FK_PRODUCTO_SUCURSAL
GO

USE master
GO
ALTER DATABASE EPATEC SET  READ_WRITE 
GO