INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Arthur','Ramirez','aramirez@gmail.com','1996-05-23', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Peter','Ramirez','peter@gmail.com','1996-05-22', '');
INSERT INTO clientes (nombre,apellido, email, create_at, foto) VALUES ('Sam','Salazar','peter@gmail.com','1999-05-22', '');

INSERT INTO productos (nombre,precio,create_at) VALUES ('Monitor',3000,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Mouse',200,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Teclado',500,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Audifonos Sonic',800,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Audifonos ACME',200,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Cargador',100,NOW());
INSERT INTO productos (nombre,precio,create_at) VALUES ('Cargador tipo C',150,NOW());

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura de Audifonos','',1,NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2,1,4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1,1,5);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura de dispositivos para PC', 'Alguna nota importante', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1,2,1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1,2,2);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1,2,3);

INSERT INTO users (username,password,enabled) VALUES('arthur','$2a$10$U4MAKzMPOt/SIUBckFG7eOlxNg/yzb6xTu932e98jLni4/8265R92',1);
INSERT INTO users (username,password,enabled) VALUES('admin','$2a$10$EzsXBEhHcvIj4v9kZiy15OFe83FXlICqp4A8Wyuzs9SrpmEJxB0Am',1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');