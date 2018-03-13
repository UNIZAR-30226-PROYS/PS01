public class listaReproduccionVO {
	private String nombreLista;
	private String nombreUsuario;

	public listaReproduccionVO(String nombreLista, String nombreUsuario){
		this.nombreLista = nombreLista;
		this.nombreUsuario = nombreUsuario;
	}

	public String obtenerNombreLista(){
		return nombreLista;
	}

	public String obtenerNombreUsuario(){
		return nombreUsuario;
	}

	public void modificarNombreLista(String nuevoNombre){
		if (nuevoNombre != NULL){
			nombreLista = nuevoNombre;
		}
	}

	public void modificarNombreUsuario(String nuevoNombre){
		if (nuevoNombre != NULL){
			nombreUsuario = nuevoNombre;
		}
	}
}