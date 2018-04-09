package pruebas.servlet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import modelo.FuncionesAuxiliares;
import org.json.simple.*;
import org.json.simple.parser.*;

/*
 * Prueba el servlet de inicio de sesión, introduciendo un usuario y la
 * contraseña de este usuario
 */
public class IniciarSesion {
	public static final String NOMBRE = "Paco";
	public static final String HASH_PW = FuncionesAuxiliares.crearHash("prueba");
	
	/*
	 * Pre:  ---
	 * Post: Ha logeado al usuario 'usuario' que tiene hash 'contrasenya'
	 * 		 en el servidor. Devuelve el identificador de la sesion si todo
	 * 		 ha ido bien
	 */
	public static String logear() {
		try {
			// Creamos las cosas que son necesarios
			URL url = new URL(FuncionesAuxiliares.URL_SERVER + "IniciarSesion");
			Map<String, Object> params = new LinkedHashMap<>();
	 
			// Metemos los parámetros necesarios y los tratamos
	        params.put("nombre", NOMBRE);
	        params.put("hashPass", HASH_PW);
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	            if (postData.length() != 0)
	                postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
	                    "UTF-8"));
	        }
	        
	        // Enviamos los parámetros al servlet
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length",
	                String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);
	        
	        // Leemos la respuesta del servlet
	        InputStream response = conn.getInputStream();
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject)jsonParser.parse(
	        	      new InputStreamReader(response, "UTF-8"));
	        String error = (String) jsonObject.get("error");
	        String login = (String) jsonObject.get("login");
	        String idSesion = (String) jsonObject.get("idSesion");
	        
	        // Comprobamos los parámetros
	        if(error != null) {
	        	System.out.println(error);
	        }
	        else if(login==null) {
	        	System.out.println("Error en el login recibido");
	        }
	        else if(idSesion==null) {
	        	System.out.println("Error en el id de sesion recibido");
	        }
	        else{
	        	System.out.println("CORRECTO!");
	        }
	        return idSesion;
		}
		catch(MalformedURLException e) {
			System.out.println("URL no existente");
			return "";
		}
		catch(Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			return "";
		}
		
	}

	public static void main(String[] args) {
		logear();
	}

}
