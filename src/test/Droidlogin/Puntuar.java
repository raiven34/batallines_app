/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Droidlogin;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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
public class Puntuar extends Activity {
    
	private ListView lista;
        private Button guardar;
        String user,ult_jornada,movil_id;
        JSONArray jdata,respuesta;
        JSONObject b;
        String URL_connect="http://batallines.es/json/jugadores_activos.php";//ruta en donde estan nuestros archivos
        String imagen;
        String nota;
        TextView txt_usr;
    private Httppostaux post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        setContentView(R.layout.puntuar);
        txt_usr= (TextView) findViewById(R.id.usr_name2);
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   ult_jornada  = extras.getString("ult_jornada");
                   movil_id = extras.getString("ult_jornada");
            }else{
         	   user="error";
            }
            
            txt_usr.setText(user);
            //cambiamos texto al nombre del usuario logueado
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
        post=new Httppostaux();
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		postparameters2send.add(new BasicNameValuePair("tipo","b"));
		    		postparameters2send.add(new BasicNameValuePair("usuario",user));
        ArrayList<test.Droidlogin.Lista_entrada_puntuar> datos = new ArrayList<test.Droidlogin.Lista_entrada_puntuar>();

		   //realizamos una peticion y como respuesta obtenes un array JSON
      	try {
        	jdata=post.getserverdata(postparameters2send, URL_connect);
        	
	        for (int i=0;i<jdata.length();i++) {						
					
				JSONObject e = jdata.getJSONObject(i);
				datos.add(new test.Droidlogin.Lista_entrada_puntuar(e.getString("foto"), e.getString("apodo"), e.getInt("nota")));
							
			}		
        } catch(JSONException e) {
        	 Log.e("log_tag", "Error parsing data "+e.toString());
        }
            

        
          
        
 
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setScrollingCacheEnabled(true);
        lista.setAdapter(new test.Droidlogin.Lista_adaptador(this, R.layout.entrada_puntuar, datos){
			@Override
			public void onEntrada(final Object entrada, View view) {
                            JugadoresHolder holder;
                            String imageUrl = "http://batallines.es/" + ((test.Droidlogin.Lista_entrada_puntuar) entrada).get_idImagen();
		        if (entrada != null) {
		            holder = new JugadoresHolder();
                            view.setTag(holder);
                            holder = (JugadoresHolder) view.getTag();
                            holder.textView_superior = (TextView) view.findViewById(R.id.textView_superior);
                            holder.textView_superior.setText(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima());
                            //TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
		            //if (texto_superior_entrada != null) 
		            //	texto_superior_entrada.setText(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima()); 
		              
//		            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
//		            if (texto_inferior_entrada != null)
//		            	texto_inferior_entrada.setText(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoDebajo()); 
		             holder.SpinnerFeedbackType = (Spinner) view.findViewById(R.id.SpinnerFeedbackType);
                             
                             holder.SpinnerFeedbackType.setTag(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima());
                             holder.SpinnerFeedbackType.setSelection(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_Nota()-1); 
                                                        
                        
                             holder.SpinnerFeedbackType.setOnItemSelectedListener(new OnItemSelectedListener(){
                                
                                     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
                                                 int position, long id) {
                                                 
                                                try {
                                                    for (int a=0;a<jdata.length();a++) {						

                                                            b = jdata.getJSONObject(a);
                                                            
                                                            
                                                            if(parentView.getTag()== b.getString("apodo")){
                                                                b.put("nota", parentView.getItemAtPosition(position).toString());                                                             
                                                                    int myNum = 0;

                                                                    try {
                                                                        myNum = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                                                                    } catch(NumberFormatException nfe) {
                                                                       System.out.println("Could not parse " + nfe);
                                                                    } 
                                                                ((test.Droidlogin.Lista_entrada_puntuar) entrada).set_Nota(myNum);
                                                                //Log.e("log_tag", x.getString("nota"));
                                                            }
                                                            
                                                    }
                                                    
                                                    
                                                    //Toast.makeText(parentView.getContext(), "Has seleccionado " +
                                                    //parentView.getItemAtPosition(position).toString() + parentView.getTag(), Toast.LENGTH_LONG).show();
                                                 } catch (JSONException ex) {
                                                         Logger.getLogger(Puntuar.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                     }

                                     public void onNothingSelected(AdapterView<?> parentView) {

                                     }
                                 });                                    

                             
                             //spinner_nota.setTag(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima());
                             //if (spinner_nota != null){
                                 //spinner_nota.setTag(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima());
            
                    

                             //}    
		            holder.imageView_imagen = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            int id = getResources().getIdentifier(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_textoEncima().toLowerCase(), "drawable", getPackageName()); 
                            if (id!=0){
                                holder.imageView_imagen.setImageResource(id);
                            }else{
                                holder.imageView_imagen.setImageResource(R.drawable.anonimo);
                            }
                            //if (imagen_entrada != null)
                            
		            	//imagen_entrada.setImageResource(((test.Droidlogin.Lista_entrada_puntuar) entrada).get_idImagen());
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
        

       

       

       guardar = (Button) findViewById(R.id.guardar);
       guardar.setOnClickListener(new View.OnClickListener() { 
			@Override
                        public void onClick(View view){
                            ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();

                                                            postparameters2send.add(new BasicNameValuePair("form",jdata.toString()));
                                                            postparameters2send.add(new BasicNameValuePair("usuario",user));
                                    ArrayList<test.Droidlogin.Lista_entrada_puntuar> datos = new ArrayList<test.Droidlogin.Lista_entrada_puntuar>();

                                               //realizamos una peticion y como respuesta obtenes un array JSON
                                    try {
                                            respuesta=post.getserverdata(postparameters2send, "http://batallines.es/json/json_puntuar.php");

                                            						

                                             JSONObject z = respuesta.getJSONObject(0);
                                             if(z.getInt("Success")==0){
                                                    Log.e("log_tag", "Registro duplicado");
                                                    Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                    vibrator.vibrate(200);
                                                    Toast toast1 = Toast.makeText(getApplicationContext(),"Ya has votado esta jornada", Toast.LENGTH_SHORT);
                                                    toast1.show();
                                             }else{
                                                    Log.e("log_tag", "Insertado");
                                                    Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                                    vibrator.vibrate(200);
                                                    Toast toast1 = Toast.makeText(getApplicationContext(),"Voto Guardado", Toast.LENGTH_SHORT);
                                                    toast1.show();                                                    
                                             }
                                                    		
                                    } catch(JSONException e) {
                                             Log.e("log_tag", "Error parsing data "+e.toString());
                                    }


                         }
        });
        
    }
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                Intent i=new Intent(Puntuar.this, HiScreen.class);
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