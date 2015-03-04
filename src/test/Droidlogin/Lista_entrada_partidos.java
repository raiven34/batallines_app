package test.Droidlogin;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Lista_entrada_partidos {
	 
	private String local; 
	private String visitante;
        private int goleslocal;
        private String lugar;
        private String fecha;
        private String hora;
        private String jugado;
        private String jornada;
        private String temporada;
        private int golesvisitante;
	  
	public Lista_entrada_partidos (String local,int goleslocal, String visitante, String lugar, String fecha, String hora, int golesvisitante, String jugado,String jornada,String temporada) { 
	    this.local = local; 
	    this.visitante = visitante;
	    this.goleslocal = goleslocal;
            this.lugar = lugar;
            this.fecha = fecha;
            this.hora = hora;
            this.golesvisitante = golesvisitante;
            this.jugado = jugado;
            this.jornada = jornada;
            this.temporada = temporada;
	}
	
	public String get_Local() { 
	    return local; 
	}
	
	public String get_Visitante() { 
	    return visitante; 
	}
	public int get_Goleslocal() { 
	    return goleslocal; 
	}    
        public String get_Lugar() { 
	    return lugar; 
	}
	public String get_Fecha() { 
	    return fecha; 
	}
        public String get_Hora() { 
	    return hora; 
	}
        public String get_Jugado() { 
	    return jugado; 
	}        
	public int get_Golesvisitante() { 
	    return golesvisitante; 
	}  
        public String get_Jornada() { 
	    return jornada; 
	}
        public String get_Temporada() { 
	    return temporada; 
	}  
}
