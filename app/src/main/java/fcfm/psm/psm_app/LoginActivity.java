package fcfm.psm.psm_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_register;
    Button btn_starWfb;
    Button btn_forgottenPass;
    TextView txt_username;
    TextView txt_password;

    final String APP_SHARED_PREFS = "AppPrefs";
    final int MAIN_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        init();
    }

    public void init(){
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_starWfb = (Button)findViewById(R.id.btn_startWfb);
        btn_forgottenPass = (Button)findViewById(R.id.btn_forgottenPass);
        txt_username = (TextView)findViewById(R.id.txt_userName);
        txt_password = (TextView)findViewById(R.id.txt_password);

        final SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
        String usernamePrefs = prefs.getString("username", "");
        String passwordPrefs = prefs.getString("password", "");
        Long time = prefs.getLong("timestamp", 0);
        if(!usernamePrefs.equals("") && !passwordPrefs.equals("") && time > 0){


            final Timestamp now = new Timestamp(new Date().getTime());
            long deltaTime = (now.getTime() - time) / 1000;
            if( deltaTime < (24*60*60)){
                //Ya estaba loggeado antes y a pasado menos de un dia desde su ultima conexion, usar las mismas credenciales

                prefs.edit().putLong("timestamp", now.getTime()).commit();
                openMainActivity();
                /*
                final ProgressDialog loggingIn = new ProgressDialog(this);
                loggingIn.setTitle("LOL");
                loggingIn.setMessage(getString(R.string.progress_dialog_logging_in_message));
                loggingIn.setCancelable(true);
                loggingIn.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loggingIn.dismiss();
                        prefs.edit().putLong("timestamp", now.getTime()).commit();
                        openMainActivity();
                    }
                }, 700);

                */
                /*
                    TODO: Login WebService
                */


            }

        }

        btn_starWfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(reg);
            }
        });
        btn_forgottenPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fgPass = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
                startActivity(fgPass);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                if(!username.equals("") && !password.equals("")){
                    //TODO: Verificar Log in
                    /*
                        Login WebService
                    */


                    if(true /* vereficacion con exito */){
                        SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        Timestamp timestamp = new Timestamp( new Date().getTime());
                        editor.putLong("timestamp", timestamp.getTime());
                        editor.commit();
                        openMainActivity();
                    }else{
                        showToast(getString(R.string.error_invalid_credentials));
                    }

                }else{
                    showToast(getString(R.string.error_every_field_required));
                }
            }
        });

    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void openMainActivity(){
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MAIN_ACTIVITY_REQUEST) {
            SharedPreferences prefs = getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", "");
            editor.putString("password", "");
            editor.putLong("timestamp", 0);
            editor.commit();
        }
    }
}

