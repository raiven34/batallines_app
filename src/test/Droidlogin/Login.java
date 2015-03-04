package test.Droidlogin;
/*CREADO POR SEBASTIAN CIPOLAT*/
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import test.Droidlogin.library.Httppostaux;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.CheckBox;



public class Login extends Activity {
    /** Called when the activity is first created. */
    String movil_id,ult_jornada,version,temporada;
    EditText user;
    EditText pass;
    TextView tversion;
    Button blogin;
    Button actualizar;
    CheckBox check, check2;
    Httppostaux post;
    // String URL_connect="http://www.scandroidtest.site90.com/acces.php";
    String IP_Server="localhost";//IP DE NUESTRO PC
    String URL_connect="http://batallines.es/json/acces.php";//ruta en donde estan nuestros archivos
    
    boolean result_back;
    private ProgressDialog pDialog;
    
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        version = getVersion();
        Bundle extras = getIntent().getExtras();
            //Miramos si hay que actualizar.
            if (extras != null) {
         	   //actualiza  = extras.getString("atualizar");//usuario
                        String url = "http://batallines.es/compruebaversion.php?version=" + version;
        		Intent i = new Intent(Intent.ACTION_VIEW);
        		i.setData(Uri.parse(url));
        		startActivity(i);
            }
        post=new Httppostaux();

        check= (CheckBox) findViewById(R.id.ChkMarcame);
        check2= (CheckBox) findViewById(R.id.ChkMarcame2);
        user= (EditText) findViewById(R.id.edusuario);
        pass= (EditText) findViewById(R.id.edpassword);
        blogin= (Button) findViewById(R.id.Blogin);
        actualizar=(Button) findViewById(R.id.actualizar);
        
        
        //buscamos preferencias
        SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
        String chk = settings.getString("check", "N");
        if (chk.equals("S")) {
        String nombre = settings.getString("usuario", "");
        user.setText(nombre);
        String password = settings.getString("password", "");
        pass.setText(password);
        check.setChecked(true);
        }

        
        //Login button action
        actualizar.setOnClickListener(new View.OnClickListener(){
            
        	public void onClick(View view){
        		
        		//Abre el navegador al formulario adduser.html
        		String url = "http://batallines.es/compruebaversion.php?version=" + version;
        		Intent i = new Intent(Intent.ACTION_VIEW);
        		i.setData(Uri.parse(url));
        		startActivity(i);        		
                }        	
        });
        blogin.setOnClickListener(new View.OnClickListener(){
       
        	public void onClick(View view){
        		 
        		//Extreamos datos de los EditText
        		String usuario=user.getText().toString();
        		String passw=pass.getText().toString();
        		
        		//verificamos si estan en blanco
        		if( checklogindata( usuario , passw )==true){

        			//si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros
        			
        		new asynclogin().execute(usuario,passw);        		               
        			      		
        		
        		}else{
        			//si detecto un error en la primera validacion vibrar y mostrar un Toast con un mensaje de error.
        			err_login();
        		}
        		
        	}
        													});
        
       // registrar.setOnClickListener(new View.OnClickListener(){
            
       // 	public void onClick(View view){
        		
        		//Abre el navegador al formulario adduser.html
       // 		String url = "http://"+IP_Server+"/droidlogin/adduser.html";
       // 		Intent i = new Intent(Intent.ACTION_VIEW);
       // 		i.setData(Uri.parse(url));
       // 		startActivity(i);        		
        //								}        	
        //														});
        String chk2 = settings.getString("check2", "N");
        if (chk2.equals("S")) {

        String nombre = settings.getString("usuario", "");
        user.setText(nombre);
        String password = settings.getString("password", "");
        pass.setText(password);
        check2.setChecked(true);
        blogin.callOnClick();
        }
    }
    
    //vibra y muestra un Toast
    public void err_login(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
    
    
    /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
    public boolean loginstatus(String username ,String password ) {
    	int logstatus=-1;
    	
    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();
     		
		    		postparameters2send.add(new BasicNameValuePair("usuario",username));
		    		postparameters2send.add(new BasicNameValuePair("password",password));
                                postparameters2send.add(new BasicNameValuePair("version",version));
		   //realizamos una peticion y como respuesta obtenes un array JSON
      		JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);

      		/*como estamos trabajando de manera local el ida y vuelta sera casi inmediato
      		 * para darle un poco realismo decimos que el proceso se pare por unos segundos para poder
      		 * observar el progressdialog
      		 * la podemos eliminar si queremos
      		 */
		    SystemClock.sleep(950);
		    		
		    //si lo que obtuvimos no es null
		    	if (jdata!=null && jdata.length() > 0){

		    		JSONObject json_data; //creamos un objeto JSON
					try {
						json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
						 logstatus=json_data.getInt("logstatus");//accedemos al valor
                                                 ult_jornada=json_data.getString("ult_jornada");//accedemos al valor
						 movil_id=json_data.getString("movil_id");//accedemos al valor
                                                 temporada=json_data.getString("temporada");
                                                 Log.e("loginstatus","logstatus= "+logstatus);//muestro por log que obtuvimos
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		            
		             
					//validamos el valor obtenido
		    		 if (logstatus==0){// [{"logstatus":"0"}] 
		    			 Log.e("loginstatus ", "invalido");
		    			 return false;
		    		 }
		    		 else{// [{"logstatus":"1"}]
		    			 Log.e("loginstatus ", "valido");
		    			 return true;
                                         
		    		 }
		    		 
			  }else{	//json obtenido invalido verificar parte WEB.
		    			 Log.e("JSON  ", "ERROR");
			    		return false;
			  }
    	
    }
    
          
    //validamos si no hay ningun campo en blanco
    public boolean checklogindata(String username ,String password ){
    	
    if 	(username.equals("") || password.equals("")){
    	Log.e("Login ui", "checklogindata user or pass error");
    return false;
    
    }else{
    	
    	return true;
    }
    
}           
    
/*		CLASE ASYNCTASK
 * 
 * usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
 * podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir    
 * si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
 * ademas observariamos el mensaje de que la app no responde.     
 */
    
    class asynclogin extends AsyncTask< String, String, String > {
    	 
    	String user,pass;
        protected void onPreExecute() {
        	//para el progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
		protected String doInBackground(String... params) {
			//obtnemos usr y pass
			user=params[0];
			pass=params[1];
            
			//enviamos y recibimos y analizamos los datos en segundo plano.
    		if (loginstatus(user,pass)==true){    		    		
    			return "ok"; //login valido
    		}else{    		
    			return "err"; //login invalido     	          	  
    		}
        	
		}
       
		/*Una vez terminado doInBackground segun lo que halla ocurrido 
		pasamos a la sig. activity
		o mostramos error*/
        protected void onPostExecute(String result) {

           pDialog.dismiss();//ocultamos progess dialog.
           Log.e("onPostExecute=",""+result);
           
           if (result.equals("ok")){
               SharedPreferences settings = getSharedPreferences("perfil", MODE_PRIVATE);
               SharedPreferences.Editor editor = settings.edit();                 
                editor.putString("temporada",temporada );
                editor.putString("ult_jornada",ult_jornada);
                if (check.isChecked()) {

                    editor.putString("usuario",user );
                    editor.putString("password",pass );
                    editor.putString("version",version );
                    editor.putString("check","S" );
                    editor.commit();
                }else{

                    editor.putString("check","N" );
                    editor.commit();
                }
                if (check2.isChecked()) {

                    editor.putString("usuario",user );
                    editor.putString("password",pass );
                    editor.putString("version",version );
                    editor.putString("check2","S" );
                    editor.commit();
                }else{

                    editor.putString("check2","N" );
                    editor.commit();
                }                
                Intent i=new Intent(Login.this, HiScreen.class);
                i.putExtra("user",user);
                i.putExtra("movil_id",movil_id);
                i.putExtra("ult_jornada",ult_jornada);
                //i.putExtra("temporada",temporada);
                startActivity(i); 
                finish(); 
                                
            }else{
            	err_login();
            }
            
                									}
		
        }
        
 
 private String getVersion() {
    String version = "";
    try {
        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
        version = pInfo.versionName;
    } catch (NameNotFoundException e1) {
        Log.e(this.getClass().getSimpleName(), "Name not found", e1);
    }
    return version;
}
    }

    

   
     
    //-----------------------------------------------------------------------
    
    
 

