package fcfm.psm.psm_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    TextView txt_message;
    Button btn_send;
    ListView lv_in;

    List<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txt_message = (TextView) findViewById(R.id.txt_message);
        btn_send = (Button) findViewById(R.id.btn_sendM);
        lv_in = (ListView) findViewById(R.id.lv_in);

        messages = new ArrayList<>();

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);

        lv_in.setAdapter(adapter);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mandar
            }
        });


        

    }

    private void updateListView(){
        ((BaseAdapter)lv_in.getAdapter()).notifyDataSetChanged();
    }

}
