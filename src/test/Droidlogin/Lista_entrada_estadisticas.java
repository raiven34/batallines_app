package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_estadisticas {
	 
	private String apodo; 
	private int amarillas;
        private int goles;
        private int asistencias;
        private int rojas;
        private int partidos;
        private String puntos;
	  
	public Lista_entrada_estadisticas (String apodo,int partidos, String puntos, int goles, int asistencias, int amarillas, int rojas) { 
	    this.apodo = apodo; 
	    this.goles = goles;
	    this.amarillas = amarillas;
            this.asistencias = asistencias;
            this.rojas = rojas;
            this.partidos = partidos;
            this.puntos = puntos;
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
        public int get_Partidos() { 
	    return partidos; 
	}
	public String get_Puntos() { 
	    return puntos; 
	}  
}
