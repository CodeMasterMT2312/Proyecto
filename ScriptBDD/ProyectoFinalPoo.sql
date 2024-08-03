CREATE DATABASE CineNova;
USE CineNova;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    Cedula INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_usuario ENUM('cliente', 'administrador') DEFAULT 'cliente'
);

INSERT INTO Usuarios (Cedula, nombre, apellido, correo_electronico, contraseña, tipo_usuario) VALUES
(123456789, 'Juan', 'Pérez', 'juan.perez@example.com', 'juan123', 'cliente'),
(987654321, 'Ana', 'Gómez', 'ana.gomez@example.com', 'admin123', 'administrador');

-- Tabla Películas
CREATE TABLE Peliculas (
    id_pelicula INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    director VARCHAR(100),
    reparto TEXT,
    genero VARCHAR(50),
    duracion INT,
    fecha_estreno DATE,
    clasificacion VARCHAR(10)
);



INSERT INTO Peliculas (titulo, descripcion, director, reparto, genero, duracion, fecha_estreno, clasificacion)
VALUES
('Sombras en el pasillo', 'Una familia se muda a una casa victoriana y descubre que está habitada por espíritus malignos. Deben desentrañar la oscura historia de la casa antes de que los fantasmas los atrapen para siempre.', 'Javier Morales', 'Ana Torres, Luis Martínez, Claudia Romero', 'Terror', 120, '2024-07-22', 'R'),

('Risas en la ciudad', '“Risas en la Ciudad” sigue a un grupo de amigos que se embarcan en una serie de aventuras hilarantes mientras intentan resolver un misterio en su ciudad. Entre malentendidos cómicos y situaciones absurdas, su amistad se fortalece y descubren que la risa es el mejor remedio para cualquier problema.', 'María López', 'Carlos Gómez, Marta Pérez, Julio Díaz', 'Comedia', 110, '2024-07-22', 'PG'),

('El vigilante', '“El Vigilante” sigue a un héroe enmascarado con habilidades extraordinarias que protege su ciudad de amenazas invisibles. Con un ojo siempre atento, descubre una conspiración que pone en peligro a todos los ciudadanos. Ahora, debe usar su ingenio y poderes para desmantelar la trama antes de que sea demasiado tarde.', 'Ricardo Fernández', 'David Silva, Laura Vega, Pedro Ruiz', 'Acción', 130, '2024-07-22', 'PG-13'),

('Horizontes Desconocidos', '“Horizontes Desconocidos” sigue a un grupo de exploradores que descubren un portal a un mundo desconocido lleno de maravillas y peligros. Mientras atraviesan paisajes impresionantes y enfrentan criaturas misteriosas, deben trabajar juntos para encontrar el camino de regreso a casa antes de que el portal se cierre para siempre.', 'Isabel Martínez', 'Jorge Castillo, Elena Romero, Sergio Campos', 'Aventura', 140, '2024-07-22', 'PG'),

('Espadas y Llamas', '“Espadas y Llamas” sigue a un grupo de héroes que deben unirse para enfrentar una amenaza que pone en peligro su mundo. Con habilidades únicas y un enemigo formidable, se embarcan en una épica aventura llena de batallas, alianzas y descubrimientos sorprendentes.', 'Fernando Morales', 'Ana García, Manuel Torres, Beatriz Soto', 'Fantasía', 150, '2024-07-22', 'PG-13'),

('Tesoros del Horizonte', '“Tesoros del Horizonte” sigue a un valiente capitán pirata y su tripulación en busca de un legendario tesoro escondido en una isla misteriosa. Enfrentándose a peligrosos mares, traiciones y criaturas míticas, deben usar su ingenio y coraje para superar los desafíos y reclamar el tesoro antes de que caiga en manos equivocadas.', 'Laura Fernández', 'Ricardo López, Patricia Jiménez, Oscar García', 'Aventura', 125, '2024-07-22', 'PG'),

('Caminos de la Historia', '“Caminos de la Historia” narra la épica travesía de un grupo de pioneros que, en busca de un nuevo comienzo, enfrentan desafíos y adversidades en su viaje a través de tierras desconocidas. A medida que avanzan, sus historias personales se entrelazan con los grandes eventos de la época, revelando el coraje y la determinación necesarios para forjar un nuevo destino.', 'Eduardo Sánchez', 'Luis Morales, Mariana Ruiz, Cecilia Castro', 'Drama', 135, '2024-07-22', 'PG-13'),

('Amor en el Crepúsculo', '“Amor en el Crepúsculo” cuenta la historia de dos almas que se encuentran en un momento inesperado de sus vidas. A medida que pasan tiempo juntos, descubren que el amor verdadero puede surgir en los lugares más inesperados y en los momentos más improbables. Con el telón de fondo de hermosos atardeceres, su relación florece y enfrentan desafíos que pondrán a prueba su amor y compromiso.', 'Sofía Morales', 'Alejandro Torres, Valeria Ruiz, Diego Gómez', 'Romance', 115, '2024-07-22', 'PG');

CREATE TABLE ImgPeliculas(
id_imagen INT AUTO_INCREMENT PRIMARY KEY,
Nombre_imagen Varchar(35),
imagen LONGBLOB,
id_pelicula INT,
FOREIGN KEY(id_pelicula) REFERENCES Peliculas(id_pelicula)
);


-- Tabla Salas
CREATE TABLE Salas (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
    tipo ENUM('2D', '3D', 'IMAX') NOT NULL
);

INSERT INTO Salas (nombre, capacidad, tipo) VALUES 
('Sala 1', 72, '2D'),
('Sala 2', 72, '3D'),
('Sala 3', 72, 'IMAX'),
('Sala 4', 72, '2D'),
('Sala 5', 72, '3D'),
('Sala 6', 72, 'IMAX'),
('Sala 7', 72, '2D'),
('Sala 8', 72, '3D');


-- Tabla Asientos
CREATE TABLE Asientos (
    id_asiento INT AUTO_INCREMENT PRIMARY KEY,
    id_sala INT,
    fila CHAR(1) NOT NULL,
    numero INT NOT NULL,
    tipo ENUM('estándar', 'preferencial') DEFAULT 'estándar',
    FOREIGN KEY (id_sala) REFERENCES Salas(id_sala)
);

DELIMITER $$

CREATE PROCEDURE InsertarAsientos()
BEGIN
    DECLARE id_sala INT DEFAULT 1;
    DECLARE fila CHAR(1);
    DECLARE numero INT;

    WHILE id_sala <= 8 DO
        SET fila = 'B';
        WHILE fila <= 'H' DO
            SET numero = 1;
            WHILE numero <= 13 DO
                -- Si la fila es de B a G y el asiento es 4, 5 o 6, lo omitimos
                IF (fila < 'H' AND (numero < 4 OR numero > 6)) OR fila = 'H' THEN
                    INSERT INTO Asientos (id_sala, fila, numero, tipo) 
                    VALUES (id_sala, fila, numero, 'estándar');
                END IF;
                SET numero = numero + 1;
            END WHILE;
            SET fila = CHAR(ASCII(fila) + 1);
        END WHILE;
        SET id_sala = id_sala + 1;
    END WHILE;
END$$

DELIMITER ;

CALL InsertarAsientos();


-- Tabla Funciones
CREATE TABLE Funciones (
    id_funcion INT AUTO_INCREMENT PRIMARY KEY,
    id_pelicula INT,
    id_sala INT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    idioma VARCHAR(50),
    subtitulos BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_sala) REFERENCES Salas(id_sala)
);

-- Funciones
INSERT INTO Funciones (id_pelicula, id_sala, fecha, hora, idioma, subtitulos) VALUES
(1, 1, '2024-07-25', '18:00:00', 'Español', TRUE),
(2, 2, '2024-07-25', '20:00:00', 'Inglés', FALSE),
(3, 3, '2024-07-25', '22:00:00', 'Español', TRUE),
(4, 4, '2024-07-25', '19:00:00', 'Inglés', TRUE),
(5, 5, '2024-07-25', '21:00:00', 'Español', FALSE),
(6, 6, '2024-07-25', '23:00:00', 'Inglés', TRUE),
(7, 7, '2024-07-25', '17:00:00', 'Español', TRUE),
(8, 8, '2024-07-25', '19:00:00', 'Inglés', FALSE),
(1, 1, '2024-07-26', '18:00:00', 'Español', TRUE),
(2, 2, '2024-07-26', '20:00:00', 'Inglés', FALSE),
(3, 3, '2024-07-26', '22:00:00', 'Español', TRUE),
(4, 4, '2024-07-26', '19:00:00', 'Inglés', TRUE),
(5, 5, '2024-07-26', '21:00:00', 'Español', FALSE),
(6, 6, '2024-07-26', '23:00:00', 'Inglés', TRUE),
(7, 7, '2024-07-26', '17:00:00', 'Español', TRUE),
(8, 8, '2024-07-26', '19:00:00', 'Inglés', FALSE);


-- Tabla Reservas
CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_funcion INT,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(Cedula),
    FOREIGN KEY (id_funcion) REFERENCES Funciones(id_funcion)
);

CREATE TABLE Asientos_Reservas (
    id_asiento INT,
    id_reserva INT,
    FOREIGN KEY (id_asiento) REFERENCES Asientos(id_asiento),
    FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva),
    PRIMARY KEY (id_asiento, id_reserva)
);


-- Tabla Estadísticas
CREATE TABLE Estadisticas (
    id_estadistica INT AUTO_INCREMENT PRIMARY KEY,
    id_funcion INT,
    asientos_totales INT,
    asientos_reservados INT,
    fecha DATE,
    FOREIGN KEY (id_funcion) REFERENCES Funciones(id_funcion)
);
