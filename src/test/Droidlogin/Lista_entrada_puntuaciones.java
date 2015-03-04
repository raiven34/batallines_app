package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_puntuaciones {
	private String apodo; 
	private String media;
	private int max;
        private int min;
	  
	public Lista_entrada_puntuaciones (String apodo, String media, int max, int min) { 
	    this.apodo = apodo; 
	    this.media = media; 
	    this.max = max; 
            this.min = min; 
	}
	
	public String get_Apodo() { 
	    return apodo; 
	}
	
	public int get_Max() { 
	    return max; 
	}
        public int get_Min() { 
	    return min; 
	}

	public String get_Media() {
	    return media; 
	} 
}
