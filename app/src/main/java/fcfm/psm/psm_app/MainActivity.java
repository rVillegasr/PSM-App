package fcfm.psm.psm_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import fcfm.psm.psm_app.Adapter.FragmentAdapter;
import fcfm.psm.psm_app.Database.EventCRUD;
import fcfm.psm.psm_app.Hardware.ShakeListener;
import fcfm.psm.psm_app.Model.Event;
import fcfm.psm.psm_app.Model.NetCallback;
import fcfm.psm.psm_app.Networking.Networking;

//region TODO LIST
/*
* + Logic
* X Share con facebook
* X Mapa
* X Localizacion
* N Incluir lo anterior en el share de facebook
* X Caledario
* X Raiting
* X Integracion con Picasso
* -- Chat
* X Gestures
* X Obtener los eventos por categoria
*
* + Web services
* X Obtener todos los eventos
*
* + Base de datos
* X Modificar usario: Solo guardar id usuario y nombre;
* X Hacer scripts para crear la base en MySQL
* X Stored procedures para los web services.
*
* + Diseño
* X Manejar archivo de idiomas
* X Usar valores del strings.xml en todos los controles
* X Logo para log in
* X Dejar solo el boton de login
* X Quitar el boton del calendario
* X Poner fecha del evento en el header
* X Hacer mas pequeño el boton flotante
*
* Extra:
* + Logic:
* -- Mostrar comentarios
* + Web services
* -- Obtener comentarios
* + Base de datos
* -- Crear tabla de comentarios
* + Diseño:
* -- Comentarios en los eventos
*
* */
//endregion

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    FloatingActionButton fab_Calendar;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeListener mShakeDetector;

    final String APP_SHARED_PREFS = "AppPrefs";
    final int CALENDAR = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeListener();
        mShakeDetector.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake(int count) {
                SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
                prefs.edit().putInt("selectedTab", viewPager.getCurrentItem()).commit();
                finish();
            }
        });
        ///////////////////////////////////////////////////


        new Networking(this).execute("eventos", "getEventos", new NetCallback() {

            @Override
            public void onWorkFinish(Object data) {
                String eventosJSON = (String) data;

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                TypeToken<List<Event>> token = new TypeToken<List<Event>>() {};

                final List<Event> events = gson.fromJson(eventosJSON, token.getType());

                if(events != null){
                    EventCRUD eventCRUD = new EventCRUD(MainActivity.this);
                    for(Event event : events){
                        eventCRUD.createEvent(event);
                    }
                }
            }
        });

        new Networking(this).execute("receiveChat", "getEventos", new NetCallback() {

            @Override
            public void onWorkFinish(Object data) {
                String eventosJSON = (String) data;
                Log.e("Thread chat", eventosJSON);
            }
        });


        fab_Calendar = (FloatingActionButton) findViewById(R.id.fab_Calendar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Nuestro adaptador de fragmentos
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Asignamos viewpager a este tabLayout
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE );
        int selectedTab = prefs.getInt("selectedTab", 0);

        tabLayout.setScrollPosition(selectedTab,0f,true);
        viewPager.setCurrentItem(selectedTab);


        fab_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calendar = new Intent(MainActivity.this, CalendarActivity.class);
                startActivityForResult(calendar, CALENDAR);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if(exit) {
            SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
            prefs.edit().putInt("selectedTab", viewPager.getCurrentItem()).commit();
            finish();
        }else{
            showToast("Tap again to exit");
            exit = true;
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALENDAR && resultCode == RESULT_OK){
            int id = data.getIntExtra("id", -1);

        }
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
