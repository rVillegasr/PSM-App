package fcfm.psm.psm_app.Networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fcfm.psm.psm_app.Model.Event;
import fcfm.psm.psm_app.Model.NetCallback;


/**
 * Created by evera on 11/6/2016.
 */

public class Networking extends AsyncTask<Object, Integer, Object> {
    static final int TIMEOUT = 3000;

    Context m_context;
    ProgressDialog m_progressDialog;

    public Networking(Context m_context) {
        this.m_context = m_context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        m_progressDialog = new ProgressDialog(m_context);
        m_progressDialog.setTitle("Conectando");
        m_progressDialog.setMessage("Espere...");
        m_progressDialog.setCancelable(false);
        m_progressDialog.show();;
    }

    @Override
    protected Object doInBackground(Object... params) {

        //Aqui obtenemos la url donde vamos abtener del WebService
        String action = (String) params[0];

        //La respuesta del servidor
        String responseString = "";

        switch (action)
        {
            case "eventos":
            {
                responseString = EventsRequest();
                NetCallback netCallback = (NetCallback) params[2];
                netCallback.onWorkFinish(responseString);
            } break;

            case "sendChat":
            {
                responseString = SendChat("nombre", "mensaje");
                NetCallback netCallback = (NetCallback) params[2];
                netCallback.onWorkFinish(responseString);
            } break;

            case "receiveChat":
            {
                responseString = ReceiveChat();
                NetCallback netCallback = (NetCallback) params[2];
                netCallback.onWorkFinish(responseString);
            } break;
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        m_progressDialog.dismiss();
    }

    // Metodo que lee un String desde un InputStream (Convertimos el InputStream del servidor en un String)
    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder response = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while((rLine = rd.readLine()) != null)
            {
                response.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private String EventsRequest() {
        String SERVER_PATH = "http://eventoswebservice.azurewebsites.net/api/eventos";

        //La respuesta del servidor
        String responseString = "";

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;

        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;

        try {
            url = new URL(SERVER_PATH);

            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor
            conn.setConnectTimeout(TIMEOUT);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Ya que la respuesta viene en un formato "InputStream" la convertimos a String ya que sabemos
            // la respuesta esta en hecha en json por nuestro webservice.php con el metodo inputStramToString (Realizado por nosotros)
            responseString = inputStreamToString(in);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }

        return responseString;
    }

    private String SendChat(String name, String message){
        //Url de la peticion
        String SERVER_PATH = "https://shark.000webhostapp.com/chat.php?";

        //Parametros a enviar en la peticion
        String postParams = "&function=send&name=" + name + "&message=" + message;

        //La respuesta del servidor
        String responseString = "";

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;

        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;

        try {
            url = new URL(SERVER_PATH);

            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);

            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);

            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor
            conn.setConnectTimeout(TIMEOUT);

            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el lo que se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            // Codigo de respuesta
            int responseCode = conn.getResponseCode();
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //Recepcion del JSON
            responseString = inputStreamToString(in);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }

        return responseString;
    }

    private String ReceiveChat(){
//Url de la peticion
        String SERVER_PATH = "https://shark.000webhostapp.com/chat.php?";

        //Parametros a enviar en la peticion
        String postParams = "&function=receive";

        //La respuesta del servidor
        String responseString = "";

        // Contiene la url del servidor y ademas metodos para abrir la conexion
        URL url = null;

        // Objeto por el cual se maneja la conexion y peticiones hacia el servidor
        HttpURLConnection conn = null;

        try {
            url = new URL(SERVER_PATH);

            // Con el metodo "openConnection()" se abre la conexion
            conn = (HttpURLConnection) url.openConnection();

            // setDoInput: Activa y especifica que se esperan valores de regreso del servidor (Response)
            conn.setDoInput(true);

            // setDoOutput: Activa y especifica que se enviaran valores al servidor (Request POST, GET)
            conn.setDoOutput(true);

            // setConnectTimeout: El tiempo que va a esperar la respuesta del servidor
            conn.setConnectTimeout(TIMEOUT);

            // setRequestProperty: Investigar Mime, Content-Type al hacer una peticion (HTML, PAPW);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // setFixedLengthStreamingMode: Se especifica el tamano del "request" (lo que se enviara al servidor
            conn.setFixedLengthStreamingMode(postParams.getBytes().length);

            // getOutputStream: Nos da un stream de datos para comenzar a escribir en el lo que se envia al servidor
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            // Codigo de respuesta
            int responseCode = conn.getResponseCode();
            Log.w("RESPONSE CODE", "" + responseCode);

            // getInputStream: Nos da un stream de datos para leer lo que el servidor responda (Response)
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //Recepcion del JSON
            responseString = inputStreamToString(in);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }

        return responseString;
    }
}
