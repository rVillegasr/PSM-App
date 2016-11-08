package fcfm.psm.psm_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fcfm.psm.psm_app.Model.Message;
import fcfm.psm.psm_app.Model.NetCallback;
import fcfm.psm.psm_app.Networking.Networking;

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
                // sendChat
                String message = txt_message.getText().toString();
                String name = Profile.getCurrentProfile().getName();
                new Networking(ChatActivity.this).execute("sendChat", " ", new NetCallback() {

                    @Override
                    public void onWorkFinish(Object data) {

                        String eventosJSON = (String) data;
                        Log.e("Thread chat", eventosJSON);
                    }
                }, name, message);
            }
        });


        // receiveChat
        new Networking(this).execute("receiveChat", " ", new NetCallback() {

            @Override
            public void onWorkFinish(Object data) {
                String eventosJSON = (String) data;
                TypeToken<List<Message>> token = new TypeToken<List<Message>>() {};

                final List<Message> events = new Gson().fromJson(eventosJSON, token.getType());
                for(Message message : events){
                    messages.add(message.getName() + ": " + message.getMessage());
                }
                ChatActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateListView();
                    }
                });

                Log.e("Thread chat", eventosJSON);
            }
        });


    }

    private void updateListView(){
        ((BaseAdapter)lv_in.getAdapter()).notifyDataSetChanged();
    }

}
