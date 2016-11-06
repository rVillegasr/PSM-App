package fcfm.psm.psm_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btn_regOK;
    TextView txt_regEmail;
    TextView txt_regPass;
    TextView txt_regPassConf;
    TextView txt_regUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    public void init(){
        btn_regOK = (Button) findViewById(R.id.btn_regOK);
        txt_regEmail = (TextView)findViewById(R.id.txt_regEmail);
        txt_regPass = (TextView)findViewById(R.id.txt_regPass);
        txt_regPassConf = (TextView)findViewById(R.id.txt_regPassConf);
        txt_regUser = (TextView)findViewById(R.id.txt_regUser);

        btn_regOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txt_regEmail.getText().toString();
                String password = txt_regPass.getText().toString();
                String passwordConf = txt_regPassConf.getText().toString();
                String username = txt_regUser.getText().toString();

                if(email.equals("") || password.equals("") || passwordConf.equals("") || username.equals("")) {
                    showToast(getString(R.string.error_every_field_required));
                    return;
                }

                if(!isValidEmailAddress(email)){
                    showToast(getString(R.string.error_invalid_email));
                    return;
                }

                if(password.length() < 8) {
                    showToast(getString(R.string.error_invalid_password));
                    return;
                }
                if(!password.equals(passwordConf)) {
                    showToast(getString(R.string.error_password_mismatch));
                    return;
                }
                //Pasamos todas las validaciones
                /*
                    TODO: Sign Up web service
                 */


                Intent cardReg = new Intent(RegisterActivity.this, PaymentActivity.class);
                startActivity(cardReg);

            }
        });
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
