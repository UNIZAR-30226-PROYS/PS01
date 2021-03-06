package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.GestorDeConexionesBD;
import modelo.clasesDAO.*;
import modelo.clasesVO.*;
import modelo.excepcion.*;

public class pruebasListaReproduccion {
	private listaReproduccionVO lVO = new listaReproduccionVO("lista_1", "alberto");
	private listaReproduccionDAO lDAO = new listaReproduccionDAO();
	private Connection connection = null;
	
	public pruebasListaReproduccion() throws Exception {
		connection = GestorDeConexionesBD.getConnection();
	}
	
	public void pruebaExisteLista() throws SQLException {
		try {
			if (lDAO.existeLista(lVO, connection)) {
				System.out.println("La lista " + lVO.obtenerNombreLista() + " del usuario " + lVO.obtenerNombreUsuario() + " ya existe.");
			}
			else {
				System.out.println("La lista " + lVO.obtenerNombreLista() + " del usuario " + lVO.obtenerNombreUsuario() + " no existe.");
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaInsertarLista() throws ListaYaExiste, SQLException {
		try {
			lDAO.anyadirLista(lVO, connection);
			System.out.println("Lista " + lVO.obtenerNombreLista() + " creada correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void pruebaQuitarLista() throws ListaYaExiste, SQLException {
		try {
			lDAO.quitarLista(lVO, connection);
			System.out.println("Lista " + lVO.obtenerNombreLista() + " eliminada correctamente.");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String args[]) throws Exception {
		pruebasListaReproduccion p = new pruebasListaReproduccion();
		
		p.pruebaExisteLista();
		p.pruebaInsertarLista();
		p.pruebaInsertarLista();
		p.pruebaExisteLista();
		p.pruebaQuitarLista();
		p.pruebaQuitarLista();
		p.pruebaExisteLista();
		p.pruebaInsertarLista();
	}
}
