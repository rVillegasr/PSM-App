package fcfm.psm.psm_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShareMomentActivity extends AppCompatActivity {

    Button btn_share;
    ImageButton btn_camera;
    ImageButton btn_memory;
    TextView txt_postDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_moment);

        btn_share = (Button)findViewById(R.id.btn_share);
        btn_camera = (ImageButton)findViewById(R.id.btn_camera);
        btn_memory = (ImageButton)findViewById(R.id.btn_memory);
        txt_postDescription = (TextView)findViewById(R.id.txt_postDescription);

    }
}
