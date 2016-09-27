package fcfm.psm.psm_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_register;
    Button btn_starWfb;
    Button btn_forgottenPass;
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

        btn_starWfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
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

    }
}

