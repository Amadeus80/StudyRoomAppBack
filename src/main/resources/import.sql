--USUARIOS
INSERT INTO usuarios(email, username, password) VALUES('acostaortizpablo@gmail.com', 'pablo', '$2a$10$3Xh/E6BcbaNLiIlGE6EMxesbqRfS7/RWavl6gYkRsRjjdfKJuXdHi')
INSERT INTO usuarios(email, username, password) VALUES('pepe@gmail.com', 'pepe', '$2a$10$3Xh/E6BcbaNLiIlGE6EMxesbqRfS7/RWavl6gYkRsRjjdfKJuXdHi')

INSERT INTO roles(rol) VALUES('ROLE_ADMIN')
INSERT INTO roles(rol) VALUES('ROLE_USER')

INSERT INTO usuarios_roles(USUARIO_ID, ROL_ID) VALUES(1, 1)
INSERT INTO usuarios_roles(USUARIO_ID, ROL_ID) VALUES(1, 2)
INSERT INTO usuarios_roles(USUARIO_ID, ROL_ID) VALUES(2, 2)

--HORARIOS

INSERT INTO horarios(HORA) VALUES('8:00')
INSERT INTO horarios(HORA) VALUES('9:00')
INSERT INTO horarios(HORA) VALUES('10:00')
INSERT INTO horarios(HORA) VALUES('11:00')
INSERT INTO horarios(HORA) VALUES('12:00')
INSERT INTO horarios(HORA) VALUES('13:00')
INSERT INTO horarios(HORA) VALUES('14:00')
INSERT INTO horarios(HORA) VALUES('15:00')
INSERT INTO horarios(HORA) VALUES('16:00')
INSERT INTO horarios(HORA) VALUES('17:00')
INSERT INTO horarios(HORA) VALUES('18:00')
INSERT INTO horarios(HORA) VALUES('19:00')
INSERT INTO horarios(HORA) VALUES('20:00')
INSERT INTO horarios(HORA) VALUES('21:00')
INSERT INTO horarios(HORA) VALUES('22:00')

--ASIENTOS

INSERT INTO asientos(ASIENTO) VALUES('1A')
INSERT INTO asientos(ASIENTO) VALUES('1B')
INSERT INTO asientos(ASIENTO) VALUES('1C')
INSERT INTO asientos(ASIENTO) VALUES('1D')
INSERT INTO asientos(ASIENTO) VALUES('1E')
INSERT INTO asientos(ASIENTO) VALUES('2A')
INSERT INTO asientos(ASIENTO) VALUES('2B')
INSERT INTO asientos(ASIENTO) VALUES('2C')
INSERT INTO asientos(ASIENTO) VALUES('2D')
INSERT INTO asientos(ASIENTO) VALUES('2E')
INSERT INTO asientos(ASIENTO) VALUES('3A')
INSERT INTO asientos(ASIENTO) VALUES('3B')
INSERT INTO asientos(ASIENTO) VALUES('3C')
INSERT INTO asientos(ASIENTO) VALUES('3D')
INSERT INTO asientos(ASIENTO) VALUES('3E')
INSERT INTO asientos(ASIENTO) VALUES('4A')
INSERT INTO asientos(ASIENTO) VALUES('4B')
INSERT INTO asientos(ASIENTO) VALUES('4C')
INSERT INTO asientos(ASIENTO) VALUES('4D')
INSERT INTO asientos(ASIENTO) VALUES('4E')

/* CONTACTOS */

INSERT INTO contactos(nombre_usuario, email, telefono, mensaje, resuelta, fecha) VALUES('pablo', 'acostaortizpablo@gmail.com', '642799465', 'Esto es una consulta de pablo', false, '2023-05-31')