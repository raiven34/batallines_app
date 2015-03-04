/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Droidlogin;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
public class Jugadores extends Activity {
    
	private ListView lista;
        TextView txt_usr;
        String user,ult_jornada,movil_id;
        String URL_connect="http://batallines.es/json/jugadores_activos.php";//ruta en donde estan nuestros archivos
        String imagen;
    private Httppostaux post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jugadores);
            txt_usr= (TextView) findViewById(R.id.usr_name);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   ult_jornada = extras.getString("ult_jornada");//jornada
                   movil_id = extras.getString("movil_id");
            }else{
         	   user="error";
            }
            
            txt_usr.setText(user);
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        post=new Httppostaux();
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		postparameters2send.add(new BasicNameValuePair("tipo","a"));
		    		postparameters2send.add(new BasicNameValuePair("password",""));
        ArrayList<test.Droidlogin.Lista_entrada> datos = new ArrayList<test.Droidlogin.Lista_entrada>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);
        	
	        for (int i=0;i<jdata.length();i++) {						
					
				JSONObject e = jdata.getJSONObject(i);
				datos.add(new test.Droidlogin.Lista_entrada(e.getString("foto"), e.getString("apodo"), e.getString("desc")));
							
			}		
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
            

        
          
        
 
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
                            String imageUrl = "http://batallines.es/" + ((test.Droidlogin.Lista_entrada) entrada).get_idImagen();
		        if (entrada != null) {
		            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
		            if (texto_superior_entrada != null) 
		            	texto_superior_entrada.setText(((test.Droidlogin.Lista_entrada) entrada).get_textoEncima()); 
		              
		            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
		            if (texto_inferior_entrada != null)
		            	texto_inferior_entrada.setText(((test.Droidlogin.Lista_entrada) entrada).get_textoDebajo()); 
		              
		            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            int id = getResources().getIdentifier(((test.Droidlogin.Lista_entrada) entrada).get_textoEncima().toLowerCase(), "drawable", getPackageName()); 
         

                            if (id!=0){
                               imagen_entrada.setImageResource(id);
                            }else{
                               imagen_entrada.setImageResource(R.drawable.anonimo);
                            }
                                
                                
		        }
			}
		});
        
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				test.Droidlogin.Lista_entrada elegido = (test.Droidlogin.Lista_entrada) pariente.getItemAtPosition(posicion); 
                
                CharSequence texto = "Descripción: " + elegido.get_textoDebajo();
                Toast toast = Toast.makeText(test.Droidlogin.Jugadores.this, texto, Toast.LENGTH_LONG);
                toast.show();
			}
        });
        
        
        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                Intent i=new Intent(Jugadores.this, HiScreen.class);
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