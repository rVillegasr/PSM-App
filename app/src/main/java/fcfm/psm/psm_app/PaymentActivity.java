package fcfm.psm.psm_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    Button btn_saveCard;
    TextView txt_creditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    public void init(){
        btn_saveCard = (Button)findViewById(R.id.btn_saveCard);
        txt_creditCard = (TextView)findViewById(R.id.txt_creditCard);

        btn_saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String creditCard = txt_creditCard.getText().toString();
                if(creditCard.equals("")){
                    openMainActivity();
                    return;
                }

                if(creditCard.length() < 16){
                    showToast(getString(R.string.error_invalid_credit_card));
                    return;
                }

                /*
                    TODO: Web service para guardar la tarjeta de credito
                 */

                openMainActivity();

            }
        });
    }

    void openMainActivity(){
        Intent main = new Intent(PaymentActivity.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(main);
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
