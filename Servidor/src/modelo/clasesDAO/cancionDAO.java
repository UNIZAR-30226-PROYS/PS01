package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modelo.clasesVO.cancionVO;

public class cancionDAO {
	public void anyadirCancion(cancionVO cancion, Connection connection) throws Exception {
		try {
			if (existeCancion(cancion, connection)) {
				throw new Exception("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " ya existe.");
			}
			else {
				String queryString = "INSERT INTO Cancion(titulo, nombreArtista, nombreAlbum, genero, uploader) "
						+ "VALUES (?,?,?,?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, cancion.verTitulo());
	    			preparedStatement.setString(2, cancion.verNombreArtista());
	    			preparedStatement.setString(1, cancion.verNombreAlbum());
	    			preparedStatement.setString(2, cancion.verGenero());
	    			preparedStatement.setString(1, cancion.verUploader());
	    			
	    			preparedStatement.executeUpdate();
	        	}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void quitarCancion(cancionVO cancion, Connection connection) throws Exception {
		try {
			if (!existeCancion(cancion, connection)) {
				throw new Exception("La cancion " + cancion.verTitulo() + " perteneciente al álbum"
						+ " " + cancion.verNombreAlbum() + " subida por el usuario "
						+ cancion.verUploader() + " no existe.");
			}
			else {
				String queryString = "DELETE FROM TABLE Cancion"
						+ " WHERE titulo = " + cancion.verTitulo()
						+ " AND nombreArtista = " + cancion.verNombreArtista()
						+ " AND nombreAlbum = " + cancion.verNombreAlbum()
						+ " AND uploader = " + cancion.verUploader()
						+ ";";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				
				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public boolean existeCancion(cancionVO cancion, Connection connection) throws Exception{
		try {
			String comprobacion = "SELECT *"
					+ " FROM Cancion"
					+ " WHERE titulo = " + cancion.verTitulo()
					+ " AND nombreArtista = " + cancion.verNombreArtista()
					+ " AND nombreAlbum = " + cancion.verNombreAlbum()
					+ " AND uploader = " + cancion.verUploader()
					+ ";";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(comprobacion);
	            
	        /* Execute query. */                    
	        int busquedaComp = preparedStatement.executeUpdate();
	        return (busquedaComp != 0);
		}
		catch (Exception e) {
			throw e;
		}
	}
}