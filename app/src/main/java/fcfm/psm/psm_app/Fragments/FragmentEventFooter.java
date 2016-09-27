package fcfm.psm.psm_app.Fragments;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import fcfm.psm.psm_app.ChatActivity;
import fcfm.psm.psm_app.Model.Event;
import fcfm.psm.psm_app.R;
import fcfm.psm.psm_app.ShareMomentActivity;

public class FragmentEventFooter extends Fragment {

    List<Event> eventList;
    Button btn_openChat;
    Button btn_shareMom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_footer, null);
        btn_openChat = (Button)rootView.findViewById(R.id.btn_openChat);
        btn_shareMom = (Button)rootView.findViewById(R.id.btn_shareMom);

        btn_openChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), ChatActivity.class);
                startActivity(chat);
            }
        });
        btn_shareMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(getActivity(), ShareMomentActivity.class);
                startActivity(share);
            }
        });
        return rootView;
    }
    public void init(){

    }

}
