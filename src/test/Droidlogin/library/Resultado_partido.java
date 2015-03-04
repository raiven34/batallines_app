package test.Droidlogin.library;

/** Handler para listado.
 * @author Ramon Invarato Men�ndez
 * www.jarroba.es
 */
public class Resultado_partido {
	 
	private String local; 
	private String visitante,jugado;
        private int goleslocal;
        private int golesvisitante;
	  
	public Resultado_partido (String local, String visitante, int goleslocal, int golesvisitante, String jugado) { 
	    this.local = local; 
	    this.visitante = visitante;
	    this.goleslocal = goleslocal;
            this.jugado = jugado; 
            this.golesvisitante = golesvisitante;

	}
	
	public String get_Resultado() { 
	    if(jugado.equals("S")){
                                    if(goleslocal==golesvisitante){
                                       return "e";
                                    }else if (golesvisitante<goleslocal){
                                        if(local.equals("BATALLINES FC")){
                                            return "g";
                                        }else{
                                            return "p";
                                        }
                                    }else{
                                        if(local.equals("BATALLINES FC")){
                                            return "p";
                                        }else{
                                            return "g"; 
                                        }
                                    }
                                }else{
                                    return "n";
                                } 
	}
  
}
