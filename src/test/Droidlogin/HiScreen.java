package test.Droidlogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.GCMBaseIntentService;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.Droidlogin.library.Httppostaux;
/*PANTALLA DE BIENVENIDA*/
public class HiScreen extends Activity {
	String user,ult_jornada,movil_id,temporada;
        
	TextView txt_usr; 
        Button logoff;
        Button bjug, bpun,best,bpar,bprox;
        JSONArray jdata;
        private Httppostaux post;
        private ProgressDialog pDialog;
        String URL_connect="http://batallines.es/json/json_actualizar_gcmid.php";
        private static final String SENDER_ID ="1061800814681";
        
	 public void onCreate(Bundle savedInstanceState) {
		 
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.lay_screen);
           
	        txt_usr = (TextView) findViewById(R.id.usr_name);
                logoff = (Button) findViewById(R.id.logoff);
                bjug = (Button) findViewById(R.id.bjug);
                bpun = (Button) findViewById(R.id.bpun);
                best = (Button) findViewById(R.id.best);
                bpar = (Button) findViewById(R.id.bpar);
                bprox = (Button) findViewById(R.id.bprox);
            
            Bundle extras = getIntent().getExtras();
            //Obtenemos datos enviados en el intent.
            if (extras != null) {
         	   user  = extras.getString("user");//usuario
                   movil_id  = extras.getString("movil_id");//movil_id
                   ult_jornada  = extras.getString("ult_jornada");//ult_jornada
                   //temporada = extras.getString("temporada");
                   //Log.e("edu", temporada);
            }else{
         	   user="error";
            }
            //buscamos preferencias
            SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
            final String chk = settings.getString("temporada", "2014/2015");

            txt_usr.setText(user);//cambiamos texto al nombre del usuario logueado
                GCMRegistrar.checkDevice(this);
                GCMRegistrar.checkManifest(this);
                 final String regId = GCMRegistrar.getRegistrationId(this);

                 if (regId.equals("")) {
                //Lanzamos el registro
                     Log.v("javahispano", "registramos");
                     //Toast toast1 = Toast.makeText(getApplicationContext(),"Registramos", Toast.LENGTH_SHORT);
                     //toast1.show();
                     GCMRegistrar.register(this, SENDER_ID);
                     String regId2 = GCMRegistrar.getRegistrationId(this);
                     actualizar_movil_id(user,regId2);
                } else if(regId.equals(movil_id)) {
                    Log.v("javahispano", "ya registrado");
                    //GCMRegistrar.unregister(this);
                    //Toast toast1 = Toast.makeText(getApplicationContext(),"ya registrado", Toast.LENGTH_SHORT);
                    //toast1.show(); 
                
                }else{
                    actualizar_movil_id(user,regId);
                    Log.v("javahispano", "actualizamos movil_id");
                    //Toast toast1 = Toast.makeText(getApplicationContext(),"actualizamos movil_id", Toast.LENGTH_SHORT);
                    //toast1.show(); 
                }   		   	             
	    logoff.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.
                            SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit(); 
                            editor.putString("check2","N" );
                            editor.commit();
                            pDialog = new ProgressDialog(HiScreen.this);
                                pDialog.setMessage("Cargando....");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();
                                Intent i=new Intent(HiScreen.this, Login.class);
				startActivity(i);                             
	         		finish(); 
	         									}
	         	});
	    bjug.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.      
                                pDialog = new ProgressDialog(HiScreen.this);
                                pDialog.setMessage("Cargando....");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();
                                Intent i=new Intent(HiScreen.this, Jugadores.class);
				i.putExtra("user",user);
                                i.putExtra("ult_jornada",ult_jornada);
                                i.putExtra("movil_id",movil_id);
				startActivity(i); 
                                finish();
	         									}
	         	});	
	    bpun.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.      
                            pDialog = new ProgressDialog(HiScreen.this);
                            pDialog.setMessage("Cargando....");
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();
                            post=new Httppostaux();        
                            ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
                                    
                                    postparameters2send.add(new BasicNameValuePair("usuario",user));

                                               //realizamos una peticion y como respuesta obtenes un array JSON
                                    try {
                                               
                                            jdata=post.getserverdata(postparameters2send, "http://batallines.es/json/json_acces_puntuar.php");
                                            
                                            						

                                             JSONObject z = jdata.getJSONObject(0);
                                             if(z.getInt("logstatus")==0){
                                                    Log.e("log_tag", "No ha votado");
                                                    Intent i=new Intent(HiScreen.this, Puntuar.class);
                                                    i.putExtra("user",user);
                                                    i.putExtra("ult_jornada",ult_jornada);
                                                    i.putExtra("movil_id",movil_id);
                                                    startActivity(i);
                                                    finish();
                                             }else{
                                                    Log.e("log_tag", "Ha votado");
                                                    Intent i=new Intent(HiScreen.this, Puntuaciones.class);
                                                    i.putExtra("user",user);
                                                    i.putExtra("jornada",ult_jornada);
                                                    i.putExtra("ult_jornada",ult_jornada);
                                                    i.putExtra("movil_id",movil_id);
                                                    startActivity(i);
                                                    finish();
                                             }
                                                    		
                                    } catch(JSONException e) {
                                             Log.e("log_tag", "Error parsing data "+e.toString());
                                             Intent i=new Intent(HiScreen.this, Puntuar.class);
                                             i.putExtra("user",user);
                                             i.putExtra("temporada",chk);
                                             startActivity(i);
                                    }                            
                                    //SystemClock.sleep(3950);
                                    //pDialog.dismiss();
                        }
	         	});
	    best.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.      
                                pDialog = new ProgressDialog(HiScreen.this);
                                pDialog.setMessage("Cargando....");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();         		
                                Intent i=new Intent(HiScreen.this, Estadisticas.class);
				i.putExtra("user",user);
                                i.putExtra("temporada",chk);
				i.putExtra("ult_jornada",ult_jornada);
                                i.putExtra("movil_id",movil_id);
                                i.putExtra("orden","partidos desc");
				startActivity(i); 
                                finish();
                                //pDialog.dismiss();
	         									}
	         	});
	    bpar.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.      
                                pDialog = new ProgressDialog(HiScreen.this);
                                pDialog.setMessage("Cargando....");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();         		
                                Intent i=new Intent(HiScreen.this, Partidos.class);
				i.putExtra("user",user);
                                i.putExtra("temporada",chk);
				i.putExtra("ult_jornada",ult_jornada);
                                i.putExtra("movil_id",movil_id);
				startActivity(i); 
                                finish();
                                //pDialog.dismiss();
	         									}
	         	});
            	    bprox.setOnClickListener(new View.OnClickListener(){
	         	
	         	public void onClick(View view){
    	        //'cerrar  sesion' nos regresa a la ventana anterior.      
                                pDialog = new ProgressDialog(HiScreen.this);
                                pDialog.setMessage("Cargando....");
                                pDialog.setIndeterminate(false);
                                pDialog.setCancelable(false);
                                pDialog.show();         		
                                //Intent i=new Intent(HiScreen.this, Detalle_partidos.class);
                                Intent i=new Intent(HiScreen.this, Detalle_partidos.class);
				i.putExtra("user",user);
                                i.putExtra("temporada","NO");
				i.putExtra("ult_jornada",ult_jornada);
                                i.putExtra("movil_id",movil_id);
				startActivity(i); 
                                finish();
                                //pDialog.dismiss();
	         									}
	         	});
	 }

         
         
	 
//Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	         // no hacemos nada.
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }
       
         public  void actualizar_movil_id(String usuario, String id){
                post=new Httppostaux();        
                ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();                    
                postparameters2send.add(new BasicNameValuePair("usuario",usuario));
                postparameters2send.add(new BasicNameValuePair("id",id));
                try {
                                               
                                            jdata=post.getserverdata(postparameters2send, "http://batallines.es/json/json_actualizar_gcmid.php");
                                            
                                            						

                                             JSONObject z = jdata.getJSONObject(0);
                                             if(z.getInt("logstatus")==0){
                                                    Log.e("log_tag", "Error al actualizar movil_id");
                                                    //Toast toast2 = Toast.makeText(getApplicationContext(),"Error al actualizar movil_id", Toast.LENGTH_SHORT);
                                                    //toast2.show();
                                             }else{
                                                    Log.e("log_tag", "Actualizar movil_id OK");
                                                    //Toast toast2 = Toast.makeText(getApplicationContext(),"Actualizar movil_id OK", Toast.LENGTH_SHORT);
                                                    //toast2.show();
               
                                             }
                                                    		
                 } catch(JSONException e) {
                                             Log.e("log_tag", "Error parsing data "+e.toString());

                 }                            
         }
	
}
