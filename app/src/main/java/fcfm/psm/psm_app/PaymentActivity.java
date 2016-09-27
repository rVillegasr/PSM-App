package fcfm.psm.psm_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    Button btn_saveCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    public void init(){
        btn_saveCard = (Button)findViewById(R.id.btn_saveCard);
        btn_saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
    }
}
