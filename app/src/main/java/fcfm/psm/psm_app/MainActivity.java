package fcfm.psm.psm_app;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import fcfm.psm.psm_app.Adapter.FragmentAdapter;


/*
* TODO LIST:
* + Logic
* -- Share con facebook
* -- Mapa
* -- Localizacion
* -- Incluir lo anterior en el share de facebook
* -- Caledario
* -- Raiting
* -- Integracion con Picasso
* -- Chat
* -- Gestures
* + Web services
* -- Registro
* -- Obtener todos los eventos
* --
* + Base de datos
* -- Modificar usario: Solo guardar id usuario y nombre;
* -- Hacer scripts para crear la base en MySQL
* -- Stored procedures para los web services.
*
* + Diseño
* -- Manejar archivo de idiomas
* -- Usar valores del strings.xml en todos los controles
* -- Logo para log in
* -- Dejar solo el boton de login
* -- Quitar el boton del calendario
* -- Poner fecha del evento en el header
* -- Hacer mas pequeño el boton flotante
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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Nuestro adaptador de fragmentos
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Asignamos viewpager a este tabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if(exit) {
            //LoginManager.getInstance().logOut();
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
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
