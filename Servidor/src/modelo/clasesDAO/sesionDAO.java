package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.clasesVO.sesionVO;
import modelo.excepcion.SesionExistente;
import modelo.excepcion.SesionInexistente;

public class sesionDAO {
	/*
	 * Pre:  ---
	 * Post: Ha creado una nueva sesión insertando la correspondiente tupla en
	 * 		 la tabla Sesion de la BD.
	 * 		 Si diera la casualidad de que ya existiera una sesión para ese usuario
	 * 		 con el mismo idSesion, salta una excepción 'SesionExistente'
	 */
	public void insertarSesion(sesionVO sesion, Connection connection)
			throws SesionExistente, SQLException {
		try {
			if (existeSesion(sesion, connection)) {
				throw new SesionExistente("Existe esa misma sesión");
			}
			else {
				String queryString = "INSERT INTO Sesion " +
		                "(hashSesion, nombreUsuario) " +
		            		"VALUES (?,?);";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
	        		
	        		preparedStatement.setString(1, sesion.verHashSesion());
	        		preparedStatement.setString(2, sesion.verNombreUsuario());
	        		
	        		preparedStatement.executeUpdate();
			}	        
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha borrado la sesión actual de un determinado usuario eliminando la correspondiente
	 * 		 tupla de la tabla Sesion de la BD.
	 * 		 Si el usuario no estuviera logueado y se ejecutase esta función, entonces y solo entonces
	 * 		 saltaría una excepción 'UsuarioYaLogueado'.
	 */
	public void cerrarSesion(sesionVO s, Connection connection)
			throws SesionInexistente, SQLException {
		try {
			if (existeSesion(s, connection)) {
				String queryString = "DELETE FROM Sesion "
								   + "WHERE nombreUsuario = " + s.verNombreUsuario() + " "
								   + "AND hashSesion = " + s.verHashSesion() +";";
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
		            
		        /* Execute query. */                    
		        preparedStatement.executeUpdate();				
			}
			else {
				throw new SesionInexistente("No existe una sesión con las características proporcionadas");
			}
		}
		catch (SQLException e) {
			throw e;
		}
	}
	
	/*
	 * Pre:
	 * Post: Devuelve verdad si y solo si existe una sesión
	 * 		 registrada para un determinado usuario.
	 */
	public boolean existeSesion(sesionVO s, Connection connection) throws SQLException {
		try {
			String comprobacion = "SELECT nombre "
								+ "FROM Sesion "
								+ "WHERE nombreUsuario = " + s.verNombreUsuario() + " "
								+ "AND hashSesion = " + s.verHashSesion() +";";
			
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
