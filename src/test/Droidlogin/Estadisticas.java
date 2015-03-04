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

/**
 *
 * @author medu
 */
public class Estadisticas extends Activity {
        
	private ListView lista;
        private Button cargar;
        private TextView part,not;
        private ImageView gol,asi,ama,roj;
        String user,temporada,ult_jornada,movil_id;
        JSONArray jdata,jtemp;
        JSONObject b;
        String URL_connect="http://batallines.es/json/json_recupera_estadisticas.php";//ruta en donde estan nuestros archivos
        String URL_connecttemp="http://batallines.es/json/json_temporadas.php";//ruta en donde estan nuestros archivos
        String imagen,orden;
        String nota;
        TextView txt_usr;
        private ProgressDialog pDialog;
    private Httppostaux post;
    private String[] temporadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.estadisticas);
        txt_usr= (TextView) findViewById(R.id.usr_name2);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   temporada  = extras.getString("temporada");//temporada
                   ult_jornada = extras.getString("ult_jornada");//jornada
                   movil_id = extras.getString("movil_id");
                   orden= extras.getString("orden");
            }else{
         	   user="error";
            }
            
            txt_usr.setText(user);

            //cambiamos texto al nombre del usuario logueado
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        post=new Httppostaux();
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		
		    		postparameters2send.add(new BasicNameValuePair("temporada",temporada));
                                postparameters2send.add(new BasicNameValuePair("orden",orden));
                                Log.e("log_tag", temporada);
                                Log.e("log_tag", orden);
        ArrayList<test.Droidlogin.Lista_entrada_estadisticas> datos = new ArrayList<test.Droidlogin.Lista_entrada_estadisticas>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	jdata= post.getserverdata(postparameters2send, URL_connect);
        	jtemp= post.getserverdata(postparameters2send,URL_connecttemp);
                //Log.e("temp", k.getString("temporada"));
	        
                temporadas = new String[jtemp.length()];
                for (int i=0;i<jtemp.length();i++) {
                    JSONObject e = jtemp.getJSONObject(i);
                    temporadas[i]= e.getString("temporada");
                }
                for (int i=0;i<jdata.length();i++) {						
					
				JSONObject e = jdata.getJSONObject(i);
                                
				datos.add(new test.Droidlogin.Lista_entrada_estadisticas(e.getString("apodo"),e.getInt("partidos"),e.getString("puntos"),e.getInt("goles"),e.getInt("asistencias"), e.getInt("amarillas"),e.getInt("rojas")));
							
		}		
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
            

        
          
        
 
        lista = (ListView) findViewById(R.id.ListView_est);
        lista.setScrollingCacheEnabled(true);
        lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada_estadisticas, datos){
			@Override
			public void onEntrada(final Object entrada, View view) {
                            EstadisticasHolder holder;
                            
		        if (entrada != null) {
		            holder = new EstadisticasHolder();
                            view.setTag(holder);
                            holder = (EstadisticasHolder) view.getTag();
                            holder.textView_jugador = (TextView) view.findViewById(R.id.jugador);
                            holder.textView_jugador.setText(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Apodo());
                            holder.textView_amarillas = (TextView) view.findViewById(R.id.amarillas);
                            //Log.e("log_tag", String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Amarillas()));
                            holder.textView_amarillas.setText(String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Amarillas()));
                            if(orden.equals("amarillas desc") || orden.equals("amarillas"))holder.textView_amarillas.setTextColor(Color.rgb(50,205,50));
                            holder.textView_goles = (TextView) view.findViewById(R.id.goles);
                            holder.textView_goles.setText(String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Goles()));
                            if(orden.equals("goles desc") || orden.equals("goles"))holder.textView_goles.setTextColor(Color.rgb(50,205,50));
                            holder.textView_rojas = (TextView) view.findViewById(R.id.rojas);
                            holder.textView_rojas.setText(String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Rojas()));
                            if(orden.equals("rojas desc") || orden.equals("rojas"))holder.textView_rojas.setTextColor(Color.rgb(50,205,50));
                            holder.textView_asistencias = (TextView) view.findViewById(R.id.asistencias);
                            holder.textView_asistencias.setText(String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Asistencias()));
                            if(orden.equals("asistencias desc") || orden.equals("asistencias"))holder.textView_asistencias.setTextColor(Color.rgb(50,205,50));
                            holder.textView_puntos = (TextView) view.findViewById(R.id.puntos);
                            holder.textView_puntos.setText(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Puntos());
                            if(orden.equals("puntos desc") || orden.equals("puntos"))holder.textView_puntos.setTextColor(Color.rgb(50,205,50));
                            holder.textView_partidos = (TextView) view.findViewById(R.id.partidos);
                            holder.textView_partidos.setText(String.valueOf(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Partidos()));
                            if(orden.equals("partidos desc") || orden.equals("partidos"))holder.textView_partidos.setTextColor(Color.rgb(50,205,50));
		            holder.imageView_imagen = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            int id = getResources().getIdentifier(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_Apodo().toLowerCase(), "drawable", getPackageName()); 
                            if (id!=0){
                                holder.imageView_imagen.setImageResource(id);
                            }else{
                                holder.imageView_imagen.setImageResource(R.drawable.anonimo);
                            }
                            //if (imagen_entrada != null)
                            
		            	//imagen_entrada.setImageResource(((test.Droidlogin.Lista_entrada_estadisticas) entrada).get_idImagen());
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
        
                final Spinner Spinnertemp = (Spinner) findViewById(R.id.Spinnertemporada);
                
                ArrayAdapter<String> adapter = 
                new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, temporadas);       
                adapter.setDropDownViewResource(R.layout.custom_spinner_list);
                Spinnertemp.setAdapter(adapter);
                ArrayAdapter myAdap = (ArrayAdapter) Spinnertemp.getAdapter();
                int i;
                for(i=0; i < myAdap.getCount(); i++) {
                       if(temporada.equals(myAdap.getItem(i).toString())){
                           Spinnertemp.setSelection(i);
                       }
                }
                Spinnertemp.setOnItemSelectedListener(new OnItemSelectedListener(){
                                
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
                        pDialog = new ProgressDialog(Estadisticas.this);
                        pDialog.setMessage("Cargando....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Intent i=new Intent(Estadisticas.this, Estadisticas.class);
                        i.putExtra("user",user);
                        i.putExtra("ult_jornada",ult_jornada);
                        i.putExtra("movil_id",movil_id);
                        i.putExtra("orden",orden);
                        i.putExtra("temporada",Spinnertemp.getSelectedItem().toString());
                        startActivity(i); 
                        finish();

                    }
       
                });
                not= (TextView) findViewById(R.id.not);
                not.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("puntos desc")){
                            cargar_estadisticas("puntos",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("puntos desc",Spinnertemp.getSelectedItem().toString());
                        }
                    }
       
                });
                part= (TextView) findViewById(R.id.part);
                part.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("partidos desc")){
                            cargar_estadisticas("partidos",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("partidos desc",Spinnertemp.getSelectedItem().toString());
                        }

                    }
       
                });
                gol= (ImageView) findViewById(R.id.gol);
                gol.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("goles desc")){
                            cargar_estadisticas("goles",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("goles desc",Spinnertemp.getSelectedItem().toString());
                        }

                    }
       
                });
                asi= (ImageView) findViewById(R.id.asi);
                asi.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("asistencias desc")){
                            cargar_estadisticas("asistencias",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("asistencias desc",Spinnertemp.getSelectedItem().toString());
                        }

                    }
       
                }); 
                ama= (ImageView) findViewById(R.id.ama);
                ama.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("amarillas desc")){
                            cargar_estadisticas("amarillas",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("amarillas desc",Spinnertemp.getSelectedItem().toString());
                        }

                    }
       
                });                
                roj= (ImageView) findViewById(R.id.roj);
                roj.setOnClickListener(new View.OnClickListener(){
       
                    public void onClick(View view){
                        if (orden.equals("rojas desc")){
                            cargar_estadisticas("rojas",Spinnertemp.getSelectedItem().toString());
                        }else{
                            cargar_estadisticas("rojas desc",Spinnertemp.getSelectedItem().toString());
                        }

                    }
       
                });                
        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                Intent i=new Intent(Estadisticas.this, HiScreen.class);
				i.putExtra("user",user);
                                i.putExtra("movil_id",movil_id);
                                i.putExtra("ult_jornada",ult_jornada);
				startActivity(i);
                                finish();
                              
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }      
         public void cargar_estadisticas(String ord, String temp){
                        pDialog = new ProgressDialog(Estadisticas.this);
                        pDialog.setMessage("Cargando....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Intent i=new Intent(Estadisticas.this, Estadisticas.class);
                        i.putExtra("user",user);
                        i.putExtra("ult_jornada",ult_jornada);
                        i.putExtra("movil_id",movil_id);
                        i.putExtra("orden",ord);
                        i.putExtra("temporada",temp);
                        startActivity(i); 
                        finish();                   
         }
}