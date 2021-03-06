package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import modelo.excepcion.*;

public class seguirDAO {
	/*
	 * Pre: ---
	 * Post: Ha insertado el usuario 'usuario' en la tabla Usuario de la BD.
	 * 		 Si ya existía un usuario con ese nombre, lanza una excepción
	 * 		 'UsuarioYaRegistrado'
	 */
	public void seguir(String nombreSeguidor, String nombreSeguido, Connection connection)
			throws SQLException, YaSeguido {
		try {
			// Comprobamos si el usuario ya lo sigue
			loSigue(nombreSeguidor, nombreSeguido, connection);
			throw new YaSeguido(nombreSeguidor + " ya sigue a " + nombreSeguido);
    
		}
		catch(NoSeguido e) {
			String queryString = "INSERT INTO Seguir " +
	                "(nombreSeguidor, nombreSeguido) " +
	            		"VALUES (?,?);";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			
	        preparedStatement = connection.prepareStatement(queryString);
	        preparedStatement.setString(1, nombreSeguidor);
	        preparedStatement.setString(2, nombreSeguido);
	        preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre: ---
	 * Post: Ha registrado que una canción ya no es favorita de un usuario.
	 * 		 Si se produce algún error entonces lanza una excepción 'ErrorQuitarMegusta'.
	 */
	public void dejarDeSeguir(String nombreSeguidor, String nombreSeguido, Connection connection)
			throws ErrorDejarDeSeguir, SQLException {
		try {
			String queryString = "DELETE FROM Seguir"
					+ " WHERE nombreSeguidor = ?"
					+ " AND nombreSeguido = ?;";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			preparedStatement.setString(1, nombreSeguidor);
			preparedStatement.setString(2, nombreSeguido);
			
			int busquedaComp = preparedStatement.executeUpdate();
	        if (busquedaComp == 0) {
	        		throw new ErrorDejarDeSeguir("Error al dejar de seguir al usuario "
	        				+ nombreSeguido + " por parte del usuario " + nombreSeguidor + ".");
	        }
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public JSONObject listaDeSeguidos(String nombreSeguidor, Connection connection)
		throws SinSeguidos, SQLException {
		try {
			String queryString =  "SELECT * "
								+ "FROM Seguir "
								+ "WHERE nombreSeguidor = ?;";
			
			PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
			preparedStatement.setString(1, nombreSeguidor);
			ResultSet resultado = preparedStatement.executeQuery();
			
			if (!resultado.first()) {
				throw new SinSeguidos("El usuario " + nombreSeguidor + " no sigue a nadie.");
			}
			else {
				JSONObject obj = new JSONObject();
				JSONArray array = new JSONArray();
				resultado.beforeFirst();
				while (resultado.next()) {
					JSONObject aux = new JSONObject();
					aux.put("nombreSeguidor", resultado.getString(1));
					aux.put("nombreSeguido", resultado.getString(2));
					array.add(aux);
				}
				obj.put("listaDeSeguidos", array);
				return obj;
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public JSONObject listaDeSeguidores(String nombreSeguido, Connection connection)
			throws SinSeguidores, SQLException {
			try {
				String queryString =  "SELECT * "
									+ "FROM Seguir "
									+ "WHERE nombreSeguido = ?;";
				
				PreparedStatement preparedStatement = 
		                connection.prepareStatement(queryString);
				preparedStatement.setString(1, nombreSeguido);
				ResultSet resultado = preparedStatement.executeQuery();
				
				if (!resultado.first()) {
					throw new SinSeguidores("El usuario " + nombreSeguido + " no tiene seguidores.");
				}
				else {
					JSONObject obj = new JSONObject();
					JSONArray array = new JSONArray();
					resultado.beforeFirst();
					while (resultado.next()) {
						JSONObject aux = new JSONObject();
						aux.put("nombreSeguido", resultado.getString(1));
						aux.put("nombreSeguidor", resultado.getString(2));
						array.add(aux);
					}
					obj.put("listaDeSeguidores", array);
					return obj;
				}
			}
			catch (Exception e) {
				throw e;
			}
		}
	
	/*
	 * Pre:  ---
	 * Post: Comprueba si 'seguidor' sigue a 'seguido'. Si no lo hace,
	 * 		 lanza una excepción "NoSeguido"
	 */
	public void loSigue(String seguidor, String seguido, Connection c)
			throws SQLException, NoSeguido {
		try {
			String q =  "SELECT * "
					+ "FROM Seguir "
					+ "WHERE nombreSeguido = ? AND "
					+ "nombreSeguidor = ?;";

			PreparedStatement p = c.prepareStatement(q);
			p.setString(1, seguido);
			p.setString(2, seguidor);
			ResultSet resultado = p.executeQuery();
			
			if (!resultado.first()) {
				throw new NoSeguido(seguidor + " no sigue a " + seguido);
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
		
}
