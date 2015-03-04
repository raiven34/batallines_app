package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_aestadisticas {
	private String idImagen; 
	private String textoEncima; 
	private int goles;
        private int amarillas; 
        private int rojas; 
        private int asistencias; 
	  
	public Lista_entrada_aestadisticas (String idImagen, String textoEncima, int goles, int amarillas, int rojas, int asistencias) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.goles = goles;
            this.amarillas = amarillas;
            this.rojas = rojas;
            this.asistencias = asistencias;
	}
	
	public String get_textoEncima() { 
	    return textoEncima; 
	}
	
	public int get_Goles() { 
	    return goles; 
	}
	public void set_Goles(int goles) {
        this.goles = goles;
        }
	public int get_Amarillas() { 
	    return amarillas; 
	}
	public void set_Amarillas(int amarillas) {
        this.amarillas = amarillas;
        }
        public int get_Rojas() { 
	    return rojas; 
	}
	public void set_Rojas(int rojas) {
        this.rojas = rojas;
        }
	public int get_Asistencias() { 
	    return asistencias; 
	}
	public void set_Asistencias(int goles) {
        this.asistencias = asistencias;
        }
	public String get_idImagen() {
	    return idImagen; 
	} 
}
