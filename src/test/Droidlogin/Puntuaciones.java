/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Droidlogin;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
public class Puntuaciones extends Activity {
    
	private ListView lista;
        private Button cargar;
        String user,jornada,ult_jornada,movil_id;
        
        JSONArray jdata,jdata2;
        JSONObject b;
        String URL_connect="http://batallines.es/json/json_puntuaciones.php";//ruta en donde estan nuestros archivos
        String URL_connect2="http://batallines.es/json/json_recupera_estadisticas_partido.php";//ruta en donde estan nuestros archivos
        String imagen;
        String nota;
        TextView txt_usr,local,visitante,goleslocal,golesvisitante,vs;
        private ProgressDialog pDialog;
        private Resultado_partido res;
    private Httppostaux post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.puntuaciones);
        txt_usr= (TextView) findViewById(R.id.usr_name2);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   jornada  = extras.getString("jornada");//jornada
                   ult_jornada = extras.getString("ult_jornada");//jornada
                   movil_id = extras.getString("movil_id");
                   
            }else{
         	   user="error";
            }
            
            txt_usr.setText(user);

            //cambiamos texto al nombre del usuario logueado
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        post=new Httppostaux();
        ArrayList<String> jornadas= new ArrayList<String>();
        
        for (int i=1;i<Integer.parseInt(ult_jornada)+1;i++) {
            jornadas.add(Integer.toString(i));
        }
        jornadas.add("Todas");
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		
		    		postparameters2send.add(new BasicNameValuePair("jornada",jornada));
                                postparameters2send.add(new BasicNameValuePair("temporada","2014/2015"));
        ArrayList<test.Droidlogin.Lista_entrada_puntuaciones> datos = new ArrayList<test.Droidlogin.Lista_entrada_puntuaciones>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	jdata=post.getserverdata(postparameters2send, URL_connect);
        	
	        for (int i=0;i<jdata.length();i++) {						
					
				JSONObject e = jdata.getJSONObject(i);
                                
				datos.add(new test.Droidlogin.Lista_entrada_puntuaciones(e.getString("apodo"),e.getString("media"),e.getInt("maxpuntos"),e.getInt("minpuntos")));
				//ult_jornada = e.getString("ultjornada");			
			}
                
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
      	try {
        	jdata2=post.getserverdata(postparameters2send, URL_connect2);
        	local = (TextView) findViewById(R.id.local);
                visitante = (TextView) findViewById(R.id.visitante);
                goleslocal = (TextView) findViewById(R.id.goleslocal);
                golesvisitante = (TextView) findViewById(R.id.golesvisitante);
                vs = (TextView) findViewById(R.id.vs);
                
	        						
					
				JSONObject o = jdata2.getJSONObject(0);
                                Log.e("log_tag", o.toString());
                                local.setText(o.getString("local"));
                                
                                
                                visitante.setText(o.getString("visitante"));
                                
                                goleslocal.setText(o.getString("goleslocal"));
                                
                                golesvisitante.setText(o.getString("golesvisitante"));
                                
                                res= new Resultado_partido(o.getString("local"),o.getString("visitante"),o.getInt("goleslocal"),o.getInt("golesvisitante"),o.getString("jugado"));
                                //Log.e("log_tag", "Get_resultado: " + res.get_Resultado());
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
                
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }            

        
          
        
 
        lista = (ListView) findViewById(R.id.ListView_est);
        lista.setScrollingCacheEnabled(true);
        lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada_puntuaciones, datos){
			@Override
			public void onEntrada(final Object entrada, View view) {
                            PuntuacionesHolder holder;
                            
		        if (entrada != null) {
		            holder = new PuntuacionesHolder();
                            view.setTag(holder);
                            holder = (PuntuacionesHolder) view.getTag();
                            holder.textView_jugador = (TextView) view.findViewById(R.id.jugador);
                            holder.textView_jugador.setText(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Apodo());
                            holder.textView_media = (TextView) view.findViewById(R.id.media);
                            //Log.e("log_tag", String.valueOf(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Amarillas()));
                            holder.textView_media.setText(String.valueOf(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Media()));
                            holder.textView_max = (TextView) view.findViewById(R.id.max);
                            holder.textView_max.setText(String.valueOf(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Max())); 
                            holder.textView_min = (TextView) view.findViewById(R.id.min);
                            holder.textView_min.setText(String.valueOf(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Min()));
		            holder.imageView_imagen = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            int id = getResources().getIdentifier(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_Apodo().toLowerCase(), "drawable", getPackageName()); 
                            if (id!=0){
                                holder.imageView_imagen.setImageResource(id);
                            }else{
                                holder.imageView_imagen.setImageResource(R.drawable.anonimo);
                            }
                            //if (imagen_entrada != null)
                            
		            	//imagen_entrada.setImageResource(((test.Droidlogin.Lista_entrada_puntuaciones) entrada).get_idImagen());
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
        
                final Spinner Spinnerjor = (Spinner) findViewById(R.id.Spinnerjor);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_list, jornadas);
                Spinnerjor.setAdapter(spinnerArrayAdapter);
                ArrayAdapter myAdap = (ArrayAdapter) Spinnerjor.getAdapter();
                int i;
                for(i=0; i < myAdap.getCount(); i++) {
                       if(jornada.equals(myAdap.getItem(i).toString())){
                           Spinnerjor.setSelection(i);
                       }
                }
                Spinnerjor.setOnItemSelectedListener(new OnItemSelectedListener(){
                                
                                     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
                                        int position, long id) {
                                                 //parentView.getItemAtPosition(position).toString();
                                        //Intent i=new Intent(Estadisticas.this, Estadisticas.class);
                                        //i.putExtra("user",user);
                                        //i.putExtra("temporada",parentView.getItemAtPosition(position).toString());
                                        //startActivity(i); 

                                     }

                                     public void onNothingSelected(AdapterView<?> parentView) {

                                     }
                });
                cargar= (Button) findViewById(R.id.cargar);
                cargar.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        pDialog = new ProgressDialog(Puntuaciones.this);
                        pDialog.setMessage("Cargando....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show(); 
                        Intent i=new Intent(Puntuaciones.this, Puntuaciones.class);
                        i.putExtra("user",user);
                        i.putExtra("ult_jornada",ult_jornada);
                        i.putExtra("movil_id",movil_id);
                        i.putExtra("jornada",Spinnerjor.getSelectedItem().toString());
                        startActivity(i); 
                        finish();

                    }
       
                });
                


        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                Intent i=new Intent(Puntuaciones.this, HiScreen.class);
				i.putExtra("user",user);
                                i.putExtra("movil_id",movil_id);
                                i.putExtra("ult_jornada",ult_jornada);
				startActivity(i);
                                finish();
                              
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }    

}