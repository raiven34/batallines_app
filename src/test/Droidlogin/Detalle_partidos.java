/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Droidlogin;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.Droidlogin.library.Httppostaux;
import test.Droidlogin.library.Resultado_partido;

/**
 *
 * @author medu
 */
public class Detalle_partidos extends Activity {
    
	private ListView lista;
        private Button cargar;
        String user,temporada,ult_jornada,movil_id,jornada;
        JSONArray jdata,respuesta,jdata2;
        JSONObject b;
        String URL_connect="http://batallines.es/json/json_recupera_estadisticas_partido.php";//ruta en donde estan nuestros archivos
        String imagen;
        String nota;
        TextView txt_usr,local,visitante,goleslocal,golesvisitante,fecha,lugar,hora,mvp,vs;
        ImageView foto;
        private ProgressDialog pDialog;
        private Httppostaux post;
        private Resultado_partido res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.detalle_partido);
        txt_usr= (TextView) findViewById(R.id.usr_name2);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   temporada  = extras.getString("temporada");//temporada
                   ult_jornada = extras.getString("ult_jornada");//jornada
                   jornada = extras.getString("jornada");
                   movil_id = extras.getString("movil_id");
            }else{
                  
                SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE); 
                   if(settings.getString("check", "N").equals("S")){
                      user=settings.getString("usuario", "Anonimo"); 
                   }else{
                      user="Anonimo"; 
                   }
                   
                   jornada = "";
                   temporada  = "";
            }
            
            txt_usr.setText(user);

            //cambiamos texto al nombre del usuario logueado
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        post=new Httppostaux();
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		postparameters2send.add(new BasicNameValuePair("jornada",jornada));
		    		postparameters2send.add(new BasicNameValuePair("temporada",temporada));
        ArrayList<test.Droidlogin.Lista_entrada_detalle_partido> datos = new ArrayList<test.Droidlogin.Lista_entrada_detalle_partido>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	jdata=post.getserverdata(postparameters2send, URL_connect);
        	local = (TextView) findViewById(R.id.local);
                visitante = (TextView) findViewById(R.id.visitante);
                goleslocal = (TextView) findViewById(R.id.goleslocal);
                golesvisitante = (TextView) findViewById(R.id.golesvisitante);
                fecha = (TextView) findViewById(R.id.fecha);
                lugar = (TextView) findViewById(R.id.lugar);
                hora = (TextView) findViewById(R.id.hora);
                mvp = (TextView) findViewById(R.id.mvp);
                vs = (TextView) findViewById(R.id.vs);
                foto = (ImageView) findViewById(R.id.foto);
	        						
					
				JSONObject e = jdata.getJSONObject(0);
                                
                                local.setText(e.getString("local"));
                                
                                
                                visitante.setText(e.getString("visitante"));
                                
                                goleslocal.setText(e.getString("goleslocal"));
                                
                                golesvisitante.setText(e.getString("golesvisitante"));
                                
                                fecha.setText("Fecha: " + e.getString("fecha"));
                                
                                lugar.setText("Lugar: " + e.getString("lugar"));
                                
                                hora.setText("Hora: " + e.getString("hora"));
                                //local.setTextColor(Color.rgb(219,219,112));
                                mvp.setText("MVP: ");
                                int id = getResources().getIdentifier(e.getString("mvp").toLowerCase(), "drawable", getPackageName()); 
         

                                if (id!=0){
                                   foto.setImageResource(id);
                                }else{
                                   foto.setImageResource(R.drawable.anonimo);
                                }
                                res= new Resultado_partido(e.getString("local"),e.getString("visitante"),e.getInt("goleslocal"),e.getInt("golesvisitante"),e.getString("jugado"));
                                Log.e("log_tag", "Get_resultado: " + res.get_Resultado());
                                if(res.get_Resultado().equals("g")){
                                     //Log.e("log_tag", "entra");
                                    local.setTextColor(Color.rgb(50,205,50));
                                    visitante.setTextColor(Color.rgb(50,205,50));
                                    vs.setTextColor(Color.rgb(50,205,50));
                                    goleslocal.setTextColor(Color.rgb(50,205,50));
                                    golesvisitante.setTextColor(Color.rgb(50,205,50));
                                    
                                }else if (res.get_Resultado().equals("e")){
                                    local.setTextColor(Color.rgb(219,219,112));
                                    visitante.setTextColor(Color.rgb(219,219,112));
                                    vs.setTextColor(Color.rgb(219,219,112));
                                    goleslocal.setTextColor(Color.rgb(219,219,112));
                                    golesvisitante.setTextColor(Color.rgb(219,219,112));
                                }else if (res.get_Resultado().equals("p")){
                                    local.setTextColor(Color.rgb(255,36,0));
                                    visitante.setTextColor(Color.rgb(255,36,0));
                                    vs.setTextColor(Color.rgb(255,36,0));
                                    goleslocal.setTextColor(Color.rgb(255,36,0));
                                    golesvisitante.setTextColor(Color.rgb(255,36,0));
                                }else{
                                    local.setTextColor(Color.rgb(211,211,211));
                                    visitante.setTextColor(Color.rgb(211,211,211));
                                    vs.setTextColor(Color.rgb(211,211,211));
                                    goleslocal.setTextColor(Color.rgb(211,211,211));
                                    golesvisitante.setTextColor(Color.rgb(211,211,211));
                                }
				//datos.add(new test.Droidlogin.Lista_entrada_partidos(e.getString("local"),e.getInt("goleslocal"),e.getString("visitante"),e.getString("lugar"),e.getString("fecha"), e.getString("hora"),e.getInt("golesvisitante"),e.getString("jugado")));
                 jdata2= jdata.getJSONArray(1);
                 
                 for (int i=0;i<jdata2.length();i++) { 
                     JSONObject a = jdata2.getJSONObject(i);
                     if(a.getString("jugador").equals("")){
                         
                     }else{
                         Log.e("log_tag", a.toString());
                         datos.add(new test.Droidlogin.Lista_entrada_detalle_partido(a.getString("jugador"),a.getInt("goles"),a.getInt("asistencias"), a.getInt("amarillas"),a.getInt("rojas")));
                     
                     }
                 }
                  
                 lista = (ListView) findViewById(R.id.ListView_est);
                 lista.setScrollingCacheEnabled(true);
                 lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada_detalle_partido, datos){
			@Override
			public void onEntrada(final Object entrada, View view) {
                            EstadisticasHolder holder;
                            
		        if (entrada != null) {
		            holder = new EstadisticasHolder();
                            view.setTag(holder);
                            holder = (EstadisticasHolder) view.getTag();
                            holder.textView_jugador = (TextView) view.findViewById(R.id.jugador);
                            holder.textView_jugador.setText(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Apodo());
                            holder.textView_amarillas = (TextView) view.findViewById(R.id.amarillas);
                            //Log.e("log_tag", String.valueOf(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Amarillas()));
                            holder.textView_amarillas.setText(String.valueOf(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Amarillas()));
                            holder.textView_goles = (TextView) view.findViewById(R.id.goles);
                            holder.textView_goles.setText(String.valueOf(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Goles())); 
                            holder.textView_rojas = (TextView) view.findViewById(R.id.rojas);
                            holder.textView_rojas.setText(String.valueOf(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Rojas()));
                            holder.textView_asistencias = (TextView) view.findViewById(R.id.asistencias);
                            holder.textView_asistencias.setText(String.valueOf(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Asistencias()));
   
   
		            holder.imageView_imagen = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            int id = getResources().getIdentifier(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_Apodo().toLowerCase(), "drawable", getPackageName()); 
                            if (id!=0){
                                holder.imageView_imagen.setImageResource(id);
                            }else{
                                holder.imageView_imagen.setImageResource(R.drawable.anonimo);
                            }
                            //if (imagen_entrada != null)
                            
		            	//imagen_entrada.setImageResource(((test.Droidlogin.Lista_entrada_detalle_partido) entrada).get_idImagen());
//                                try {
                                  
//                                  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
//                                  holder.imageView_imagen.setImageBitmap(bitmap);
                                  //setContentView(image);
//                                } catch (MalformedURLException e) {
//                                  e.printStackTrace();
//                                } catch (IOException e) {
//                                  e.printStackTrace();
//                                }                                
		        }else{
                            //holder = (JugadoresHolder) view.getTag();
                            //holder.textView_superior.setText(datos.get(posicion).getNumero());
                        }
			}
		});
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
            

        
          
        
 
                


        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                if(user.equals("Anonimo")){
                                    Intent i=new Intent(Detalle_partidos.this, Login.class);
                                    startActivity(i);
                                    finish();  
                                }else{
                                    if(temporada.equals("NO")){
                                        Intent i=new Intent(Detalle_partidos.this, HiScreen.class);
                                        i.putExtra("user",user);
                                        i.putExtra("movil_id",movil_id);
                                        i.putExtra("ult_jornada",ult_jornada);
                                        //i.putExtra("temporada",temporada);
                                        startActivity(i);
                                        finish();
                                    }else{
                                            Intent i=new Intent(Detalle_partidos.this, HiScreen.class);
                                            i.putExtra("user",user);
                                            i.putExtra("movil_id",movil_id);
                                            i.putExtra("ult_jornada",ult_jornada);
                                            //i.putExtra("temporada",temporada);
                                            startActivity(i);
                                            finish(); 
                                    }
                                }
                              
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }      

}