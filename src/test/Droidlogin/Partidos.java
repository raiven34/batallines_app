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
public class Partidos extends Activity {
    
	private ListView lista;
        private Button cargar;
        String user,temporada,ult_jornada,movil_id,jornada;
        JSONArray jdata,jtemp;
        JSONObject b;
        String URL_connect="http://batallines.es/json/json_recupera_partidos.php";//ruta en donde estan nuestros archivos
        String URL_connecttemp="http://batallines.es/json/json_temporadas.php";//ruta en donde estan nuestros archivos
        String imagen;
        String nota;
        TextView txt_usr;
        private ProgressDialog pDialog;
        private String[] temporadas;
    private Httppostaux post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.partidos);
        txt_usr= (TextView) findViewById(R.id.usr_name2);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   temporada  = extras.getString("temporada");//temporada
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
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		
		    		postparameters2send.add(new BasicNameValuePair("temporada",temporada));
        ArrayList<test.Droidlogin.Lista_entrada_partidos> datos = new ArrayList<test.Droidlogin.Lista_entrada_partidos>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	jdata=post.getserverdata(postparameters2send, URL_connect);
        	jtemp= post.getserverdata(postparameters2send,URL_connecttemp);
                temporadas = new String[jtemp.length()];
                for (int i=0;i<jtemp.length();i++) {
                    JSONObject e = jtemp.getJSONObject(i);
                    temporadas[i]= e.getString("temporada");
                }        	
	        for (int i=0;i<jdata.length();i++) {						
					
				JSONObject e = jdata.getJSONObject(i);
                                
				datos.add(new test.Droidlogin.Lista_entrada_partidos(e.getString("local"),e.getInt("goleslocal"),e.getString("visitante"),e.getString("lugar"),e.getString("fecha"), e.getString("hora"),e.getInt("golesvisitante"),e.getString("jugado"),e.getString("jornada"),e.getString("temporada")));
							
			}		
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
            

        
          
        
 
        lista = (ListView) findViewById(R.id.ListView_est);
        lista.setScrollingCacheEnabled(true);
        lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada_partidos, datos){
			@Override
			public void onEntrada(final Object entrada, View view) {
                            PartidosHolder holder;
                            
		        if (entrada != null) {
		            holder = new PartidosHolder();
                            view.setTag(holder);
                            holder = (PartidosHolder) view.getTag();
                            
                            holder.textView_local = (TextView) view.findViewById(R.id.local);
                            holder.textView_local.setText(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Local());
                            holder.textView_goleslocal = (TextView) view.findViewById(R.id.goleslocal);
                            holder.textView_goleslocal.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Goleslocal()));
                            holder.textView_golesvisitante = (TextView) view.findViewById(R.id.golesvisitante);
                            holder.textView_golesvisitante.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Golesvisitante()));
                            //Log.e("log_tag", String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Amarillas()));
                            holder.textView_visitante = (TextView) view.findViewById(R.id.visitante);
                            holder.textView_visitante.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Visitante()));
                            if(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Jugado().equals("S")){
                                if(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Golesvisitante()==((test.Droidlogin.Lista_entrada_partidos) entrada).get_Goleslocal()){
                                   holder.textView_local.setTextColor(Color.rgb(219,219,112));
                                   holder.textView_goleslocal.setTextColor(Color.rgb(219,219,112));
                                   holder.textView_golesvisitante.setTextColor(Color.rgb(219,219,112));
                                   holder.textView_visitante.setTextColor(Color.rgb(219,219,112));
                                   holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                   holder.textView_vs.setTextColor(Color.rgb(219,219,112));
                                }else if (((test.Droidlogin.Lista_entrada_partidos) entrada).get_Golesvisitante()<((test.Droidlogin.Lista_entrada_partidos) entrada).get_Goleslocal()){
                                    if(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Local().equals("BATALLINES FC")){
                                        holder.textView_local.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_goleslocal.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_golesvisitante.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_visitante.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                        holder.textView_vs.setTextColor(Color.rgb(50,205,50)); 
                                    }else{
                                        holder.textView_local.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_goleslocal.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_golesvisitante.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_visitante.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                        holder.textView_vs.setTextColor(Color.rgb(255,36,0)); 
                                    }
                                }else{
                                    if(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Visitante().equals("BATALLINES FC")){
                                        holder.textView_local.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_goleslocal.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_golesvisitante.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_visitante.setTextColor(Color.rgb(50,205,50));
                                        holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                        holder.textView_vs.setTextColor(Color.rgb(50,205,50)); 
                                    }else{
                                        holder.textView_local.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_goleslocal.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_golesvisitante.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_visitante.setTextColor(Color.rgb(255,36,0));
                                        holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                        holder.textView_vs.setTextColor(Color.rgb(255,36,0)); 
                                    }
                                }
                            }else{
                                        holder.textView_local.setTextColor(Color.rgb(211,211,211));
                                        holder.textView_goleslocal.setTextColor(Color.rgb(211,211,211));
                                        holder.textView_golesvisitante.setTextColor(Color.rgb(211,211,211));
                                        holder.textView_visitante.setTextColor(Color.rgb(211,211,211));
                                        holder.textView_vs = (TextView) view.findViewById(R.id.vs);
                                        holder.textView_vs.setTextColor(Color.rgb(211,211,211));
                            }
                            //holder.textView_fecha = (TextView) view.findViewById(R.id.fecha);
                            //holder.textView_fecha.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Fecha())); 
                            //holder.textView_hora = (TextView) view.findViewById(R.id.hora);
                            //holder.textView_hora.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Hora()));
                            //holder.textView_lugar = (TextView) view.findViewById(R.id.lugar);
                            //holder.textView_lugar.setText(String.valueOf(((test.Droidlogin.Lista_entrada_partidos) entrada).get_Lugar()));
   

                            //if (imagen_entrada != null)
                            
		            	//imagen_entrada.setImageResource(((test.Droidlogin.Lista_entrada_partidos) entrada).get_idImagen());
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
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
                                @Override
                                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                                        test.Droidlogin.Lista_entrada_partidos elegido = (test.Droidlogin.Lista_entrada_partidos) pariente.getItemAtPosition(posicion); 
                                        jornada = elegido.get_Jornada();
                                        pDialog = new ProgressDialog(Partidos.this);
                                        pDialog.setMessage("Cargando....");
                                        pDialog.setIndeterminate(false);
                                        pDialog.setCancelable(false);
                                        pDialog.show();
                                        Intent i=new Intent(Partidos.this, Detalle_partidos.class);
                                        i.putExtra("user",user);
                                        i.putExtra("ult_jornada",ult_jornada);
                                        i.putExtra("movil_id",movil_id);
                                        i.putExtra("temporada",elegido.get_Temporada());
                                        i.putExtra("jornada",elegido.get_Jornada());
                                        startActivity(i); 
                                        finish();
                                        //CharSequence texto = "Descripción: " + elegido.get_Fecha() + " a las " + elegido.get_Hora();
                                        //Toast toast = Toast.makeText(test.Droidlogin.Partidos.this, texto, Toast.LENGTH_LONG);
                                        //toast.show();
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
                        pDialog = new ProgressDialog(Partidos.this);
                        pDialog.setMessage("Cargando....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(false);
                        pDialog.show();
                        Intent i=new Intent(Partidos.this, Partidos.class);
                        i.putExtra("user",user);
                        i.putExtra("ult_jornada",ult_jornada);
                        i.putExtra("movil_id",movil_id);
                        i.putExtra("temporada",Spinnertemp.getSelectedItem().toString());
                        startActivity(i); 
                        finish();

                    }
       
                });
                


        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                Intent i=new Intent(Partidos.this, HiScreen.class);
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