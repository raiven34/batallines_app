package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_detalle_partido {
	 
	private String apodo; 
	private int amarillas;
        private int goles;
        private int asistencias;
        private int rojas;
        
        
	  
	public Lista_entrada_detalle_partido (String apodo, int goles, int asistencias, int amarillas, int rojas) { 
	    this.apodo = apodo; 
	    this.goles = goles;
	    this.amarillas = amarillas;
            this.asistencias = asistencias;
            this.rojas = rojas;
            
	}
	
	public String get_Apodo() { 
	    return apodo; 
	}
	
	public int get_Amarillas() { 
	    return amarillas; 
	}
	public int get_Goles() { 
	    return goles; 
	}    
        public int get_Asistencias() { 
	    return asistencias; 
	}
	public int get_Rojas() { 
	    return rojas; 
	}

}
