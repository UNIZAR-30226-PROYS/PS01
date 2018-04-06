package pruebas.servlet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import modelo.FuncionesAuxiliares;

/*
 * Prueba el servlet de inicio de sesión, introduciendo un usuario y la
 * contraseña de este usuario
 */
public class IniciarSesion {

	public static void main(String[] args) {
		try {
			// Creamos las cosas que son necesarios
			URL url = new URL(FuncionesAuxiliares.URL_SERVER + "IniciarSesion");
			Map<String, Object> params = new LinkedHashMap<>();
	 
			// Metemos los parámetros necesarios y los tratamos
	        params.put("nombre", "Paco");
	        params.put("hashPass", "Pil");
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	            if (postData.length() != 0)
	                postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	                    "UTF-8"));
	        }
	        
	        // Enviamos los parámetros
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length",
	                String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        // Leemos los parámetros
	        InputStream response = conn.getInputStream();
	        java.util.Scanner scanner = new java.util.Scanner(response,"UTF-8").useDelimiter("\\A");
	        String theString = scanner.hasNext() ? scanner.next() : "";
	        System.out.println(theString);
	        scanner.close();
	        
	        System.out.println("Exito...");
	        
	       
		}
		catch(MalformedURLException e) {
			System.out.println("URL no existente");
		}
		catch(Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
		}
	}

}
