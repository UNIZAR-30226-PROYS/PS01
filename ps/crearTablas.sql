DROP DATABASE IF EXISTS software;
CREATE DATABASE software;
USE software;

CREATE TABLE Usuario(
	nombre varchar(32) PRIMARY KEY,
	hashPass varchar(128) NOT NULL
);

CREATE TABLE Cancion(
	titulo varchar(32),
	nombreArtista varchar(32),
	nombreAlbum varchar(32) default 'Desconocido',
	genero varchar(32) default 'Desconocido',
	uploader varchar(32) REFERENCES Usuario(nombre),
	ruta varchar(32) UNIQUE NOT NULL,
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, uploader),
	FOREIGN KEY (uploader) references Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE Sesion(
	hashSesion varchar(128),
	nombreUsuario varchar(32),
	PRIMARY KEY (hashSesion, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

-- 'nombreSeguidor' se refiere a ti como persona que sigue a otra persona,
-- NO las personas que te siguen a ti
-- 'nombreSeguido' se refiere a la persona a la que sigues
CREATE TABLE Seguir(
	nombreSeguidor varchar(32),
	nombreSeguido varchar(32),
	PRIMARY KEY (nombreSeguidor, nombreSeguido),
	FOREIGN KEY (nombreSeguidor) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	FOREIGN KEY (nombreSeguido) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE ListaReproduccion(
	nombre varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (nombre, nombreUsuario),
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE
);

CREATE TABLE Formar(
	titulo varchar(32),
	nombreArtista varchar(32),
	nombreAlbum varchar(32),
	nombreLista varchar(32),
	nombreUsuario varchar(32),
	PRIMARY KEY (titulo, nombreArtista, nombreAlbum, nombreLista, nombreUsuario),
	FOREIGN KEY (titulo, nombreArtista, nombreAlbum) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum) ON DELETE CASCADE,
	FOREIGN KEY (nombreLista, nombreUsuario) REFERENCES ListaReproduccion(nombre, nombreUsuario) ON DELETE CASCADE
);

CREATE TABLE Reproducción(
	nombreUsuario varchar(32) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	titulo varchar(32),
	nombreAlbum varchar(32),
	nombreArtista varchar(32),
	fecha TIMESTAMP default CURRENT_TIMESTAMP,
	FOREIGN KEY (titulo, nombreAlbum, nombreArtista) REFERENCES Cancion(titulo, nombreArtista, nombreAlbum) ON DELETE CASCADE,
	FOREIGN KEY (nombreUsuario) REFERENCES Usuario(nombre) ON DELETE CASCADE,
	PRIMARY KEY (nombreUsuario, titulo, nombreAlbum, nombreArtista, fecha)
);