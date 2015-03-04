/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.Droidlogin;
 
 
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


 
import com.google.android.gcm.GCMBaseIntentService;
 
public class GCMIntentService extends GCMBaseIntentService{
 
private static final String SENDER_ID ="1061800814681";
public GCMIntentService() {
super(SENDER_ID);
}
 
@Override
protected void onError(Context ctx, String registrationId) {
Log.d("javahispano",
"Error recibido");
 
}
 
@Override
protected void onMessage(Context ctx, Intent intent) {
String msg = intent.getExtras().getString("message");
String tipo = intent.getExtras().getString("tipo");
Log.d("javahispano",
"Mensaje:" + msg);
mostrarNotificacion(ctx, msg,tipo);
//createNotification(msg);
Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
vibrator.vibrate(200);
 
}
 
@Override
protected void onRegistered(Context ctx, String regId) {
//Posteriormente enviaremos el id al Tomcat
Log.d("javahispano", "Registro recibido " + regId);
}
 
@Override
protected void onUnregistered(Context ctx, String regId) {
//Posteriormente enviaremos el id al Tomcat
Log.d("javahispano",
"Baja:" + regId);
}
private void mostrarNotificacion(Context ctx, String msg,String tipo)
{
        CharSequence ticker ="Mensaje Recibido";
        CharSequence contentTitle = "sin";
        CharSequence bigcontentTitle = "sin";
        Intent i;
        if (tipo.equals("1")){
            contentTitle ="Próxima Jornada";
            bigcontentTitle ="Próxima Jornada";
            i = new Intent(getApplicationContext(), Detalle_partidos.class);
        }else if(tipo.equals("2")){
            contentTitle ="Nueva Versión Disponible";
            bigcontentTitle ="Nueva Versión Disponible";
            i = new Intent(getApplicationContext(), Login.class);
            i.putExtra("actualiza", "S");
        }else{
            contentTitle ="Mensaje Recibido";
            bigcontentTitle ="Mensaje Recibido";
            i = new Intent(getApplicationContext(), Login.class);
        }
        
        //i.putExtra("notificationID", 0);
         
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        

        
        //CharSequence contentText = "Visita ahora SekthDroid!";
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Notification noti = new Notification.Builder(ctx)
//                                .setContentIntent(pendingIntent)
//                                .setTicker(ticker)
//                                .setContentTitle(contentTitle)
//                                .setContentText(msg)
//                                .setSmallIcon(R.drawable.ic_launcher)
//                                //.addAction(R.drawable.ic_launcher, ticker, pendingIntent)
//                                .setVibrate(new long[] {100, 250, 100, 500})
//                                .setSound(alarmSound)
//                                .setLights(Color.YELLOW, 500, 2000)
//                                .setAutoCancel(true)
//                                .build();
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        Notification.Builder builder = new Notification.Builder(ctx);
                                builder.setContentIntent(pendingIntent)
                                .setTicker(ticker)
                                .setContentTitle(contentTitle)
                                .setContentText(msg)
                                .setSmallIcon(R.drawable.ic_launcher_mini)
                                .setLargeIcon(largeIcon)
                                //.addAction(R.drawable.ic_launcher, ticker, pendingIntent)
                                .setVibrate(new long[] {100, 250, 100, 500})
                                .setSound(alarmSound)
                                .setLights(Color.YELLOW, 500, 2000)
                                .setAutoCancel(true);
                                //.build();
        Notification.BigTextStyle builderBig = new Notification.BigTextStyle(builder);
			builderBig.bigText(msg);
			//builderBig.setBigContentTitle("Batallines Informa");
                        builderBig.setBigContentTitle(bigcontentTitle);
                        //builderBig.setSummaryText("A Ganar Batallos!");
                        
                        
                        
        nm.notify(0, builder.build());    

}
private void createNotification(String msg) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
    //      Notification notificacion = new NotificationBuilder(this)
    //        .setContentTitle("New mail from " + "test@gmail.com")
    //        .setContentText("Subject")
    //        .setSmallIcon(R.drawable.ic_launcher);
         
        //indicamos icono, texto emergente en la barra y la fecha que queramos
        Notification notificacion = new Notification(R.drawable.ic_launcher, msg, System.currentTimeMillis());
        //intent hacia la activity que se ejecutará cuando se pulse la notificación. Enviamos un parámetro para saber que llegamos
        //a la activity desde la notificación y mostrar un Toast
        Intent notificacionIntent = new Intent(getApplicationContext(), Detalle_partidos.class);
        //notificacionIntent.putExtra(DESDE_NOTIFICACION, true);
        PendingIntent notificacionPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificacionIntent, 0);
        //contenido de la notificación
        notificacion.setLatestEventInfo(this.getApplicationContext(), "Mensaje Recibido", msg, notificacionPendingIntent);
        //el atributo flags de la notifiacción nos permite ciertas opciones
        notificacion.flags |= Notification.FLAG_AUTO_CANCEL;//oculta la notificación una vez pulsada
        //idem para defaults
        notificacion.defaults |= Notification.DEFAULT_SOUND; //sonido
        //añadimos efecto de vibración, necesitamos el permiso <uses-permission android:name="android.permission.VIBRATE" />
        notificacion.defaults |= Notification.DEFAULT_VIBRATE;
        
        notificacion.defaults |= Notification.DEFAULT_LIGHTS;
        
        //se muestra
        notificationManager.notify(0, notificacion);     
    }

}
