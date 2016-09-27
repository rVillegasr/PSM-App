package fcfm.psm.psm_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Button btn_regOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    public void init(){
        btn_regOK = (Button) findViewById(R.id.btn_regOK);
        btn_regOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardReg = new Intent(RegisterActivity.this, PaymentActivity.class);
                startActivity(cardReg);
            }
        });
    }
}
