package modelo.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import modelo.FuncionesAuxiliares;
import modelo.ImplementacionFachada;
import modelo.clasesVO.listaReproduccionVO;

/**
 * Servlet implementation class CambiarNombreLista
 */
@WebServlet("/CambiarNombreLista")
public class CambiarNombreLista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recuperamos los parámetros y las cookies
		Cookie[] cookies = request.getCookies();
		String nombreUsuario = FuncionesAuxiliares.obtenerCookie(cookies, "login");
		String idSesion = FuncionesAuxiliares.obtenerCookie(cookies, "idSesion");
		String viejoNombre = request.getParameter("viejoNombre");
		String nuevoNombre = request.getParameter("nuevoNombre");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
		JSONObject obj = new JSONObject();
		
		// Comprobamos que no haya parámetros incorrecto
		if (nombreUsuario == null || idSesion == null){
			// Metemos el objeto de error en el JSON
			obj.put("error", "Usuario no logeado en el servidor");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(viejoNombre == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "No se ha recibido el parámetro 'viejoNombre'");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else if(nuevoNombre == null) {
			// Metemos el objeto de error en el JSON
			obj.put("error", "No se ha recibido el parámetro 'nuevoNombre'");
			
			// Respondemos con el fichero JSON
			out.println(obj.toJSONString());
		}
		else {
			try {
				ImplementacionFachada f = new ImplementacionFachada();
				FuncionesAuxiliares.existeSesion(nombreUsuario, idSesion);
				f.cambiarNombreLista(new listaReproduccionVO(viejoNombre,nombreUsuario) , nuevoNombre);
				out.println(obj.toJSONString());
			}
			catch(Exception e) {
				// Metemos el objeto de error en el JSON
				obj.put("error", e.toString());
				
				// Respondemos con el fichero JSON
				out.println(obj.toJSONString());
			}
		}
	}
}
