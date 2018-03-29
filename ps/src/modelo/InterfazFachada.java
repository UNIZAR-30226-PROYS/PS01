package modelo;

import modelo.clasesVO.*;
import modelo.excepcion.*;
import java.sql.SQLException;

/*
 * Interfaz de la fachada. Incluye las operaciones que se pueden invocar desde
 * los servlets para insertar o comprobar datos de la BD
 */
public interface InterfazFachada {
	/*
	 * Pre:  'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de su contraseña, comprueba
	 * 		 si existe una entrada en la base de datos con ese nombre de usuario
	 * 		 y ese hash de contraseña. En caso contrario, lanza una excepción
	 */
	public void iniciarSesion(String nombreUsuario, String hashPass) 
			throws LoginInexistente, SQLException;
	
	/*
	 * Pre: 'nombreUsuario' es el nombre del usuario y 'hashPass' el hash de
	 * 		 su contraseña
	 * Post: Dado el nombre de un usuario y el hash de la contraseña, lo ha
	 * 		 registrado en la base de datos. En caso de que ya existiera un
	 * 		 usuario con ese nombre, lanza una excepción 'UsuarioYaRegistrado'
	 */
	public void registrarUsuario(String nombreUsuario, String hashPass) 
			throws UsuarioYaRegistrado, SQLException;
	
	/*
	 * Pre: 'nombreUsuario' es el nombre de un usuario existente y 'idSesion'
	 * 		es el id de una nueva sesión
	 * Post: Ha registrado una nueva sesión 'id' por parte del usuario
	 * 		 'nombreUsuario'
	 */
	public void nuevaSesion(String nombreUsuario, String idSesion)
			throws SesionExistente, SQLException;
	
	/*
	 * Pre:
	 * Post: Consulta si el usuario 'nombreUsuario' tiene registrada una
	 * 		 sesión con identificador 'idSesion'. En caso de no tenerla,
	 * 		 devuelve una excepción 'SesionInexistente'
	 */
	public void existeSesionUsuario(String nombreUsuario, String idSesion)
			throws SesionInexistente, SQLException;
	
	
	public void cerrarSesion(String nombreusuario, String idSesion)
			throws SesionInexistente, SQLException;

	public void crearListaDeReproduccion(listaReproduccionVO l)
			throws ListaYaExiste, SQLException;
	
	public void borrarListaDeReproduccion(listaReproduccionVO l)
			throws ListaNoExiste, SQLException;
	
	public void megusta(gustarVO g)
			throws ErrorAnyadirMegusta, SQLException;
	
	public void yanomegusta(gustarVO g)
			throws ErrorQuitarMegusta, SQLException;
	
	public void anyadirReproduccion(reproducirVO r)
			throws ExcepcionReproduccion, SQLException;
	
	public void anyadirCancionALista(formarVO f)
			throws CancionExisteEnLista, SQLException;
	
	public void quitarCancionDeLista(formarVO f)
			throws CancionNoExisteEnLista, SQLException;
	
	public void anyadirAudicionLista(escucharVO e)
			throws ExcepcionEscuchar, SQLException;
	
	public void anyadirCancionUsuario(cancionVO c)
			throws CancionYaExiste, SQLException;
	
	public void quitarCancionUsuario(cancionVO c)
			throws CancionNoExiste, SQLException;
	
	public void anyadirArtistaAlbum(artistaAlbumVO a)
			throws ArtistaAlbumExiste, SQLException;
	
	public void quitarArtistaAlbum(artistaAlbumVO a)
			throws ArtistaAlbumNoExiste, SQLException;
}
