package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_puntuar {
	private String idImagen; 
	private String textoEncima; 
	private int id; 
	  
	public Lista_entrada_puntuar (String idImagen, String textoEncima, int id) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.id = id; 
	}
	
	public String get_textoEncima() { 
	    return textoEncima; 
	}
	
	public int get_Nota() { 
	    return id; 
	}
	public void set_Nota(int nota) {
        this.id = nota;
        }
	public String get_idImagen() {
	    return idImagen; 
	} 
}
