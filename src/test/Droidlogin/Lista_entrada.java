package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada {
	private String idImagen; 
	private String textoEncima; 
	private String textoDebajo; 
	  
	public Lista_entrada (String idImagen, String textoEncima, String textoDebajo) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.textoDebajo = textoDebajo; 
	}
	
	public String get_textoEncima() { 
	    return textoEncima; 
	}
	
	public String get_textoDebajo() { 
	    return textoDebajo; 
	}
	
	public String get_idImagen() {
	    return idImagen; 
	} 
}
