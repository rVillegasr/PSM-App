package fcfm.psm.psm_app.Networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
    static final String SERVER_PATH = "http://eventoswebservice.azurewebsites.net/api/eventos";
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

            case "chat":
            {
                responseString = EventsRequest();
                NetCallback netCallback = (NetCallback) params[2];
                netCallback.onWorkFinish(responseString);
            } break;
        }



        return (Object) responseString;
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

    //TODO pasarle el nombre y el mensaje al metodo obtenido con los parametros de arriba
    private String ChatRequest(){

        //Parametros a enviar en la peticion
        String postParams = "&action=signup&userJson=";

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
}
