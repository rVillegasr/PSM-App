package fcfm.psm.psm_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fcfm.psm.psm_app.Adapter.EventAdapter;
import fcfm.psm.psm_app.Adapter.FragmentAdapter;
import fcfm.psm.psm_app.Database.EventCRUD;
import fcfm.psm.psm_app.Model.Event;
import fcfm.psm.psm_app.Model.NetCallback;
import fcfm.psm.psm_app.Networking.Networking;

import static android.app.Activity.RESULT_OK;

public class EventListActivity extends Fragment {
    List<Event> mEventList;
    String category;
    ListView lvEvents;

    final public static String CATEGORY_FOLLOWING             = "FOLLOWING";
    final public static String CATEGORY_ALL                   = "ALL";
    final public static String CATEGORY_ART_AND_CULTURE       = "ART & CULTURE";
    final public static String CATEGORY_MUSIC                 = "MUSIC";
    final public static String CATEGORY_SCIENCE_AND_TECNOLOGY = "SCIENCE & TECNOLOGY";
    final public static String CATEGORY_THEATER               = "THEATER";
    final public static String CATEGORY_SPORTS                = "SPORTS";

    final public static int COMPLETE_EVENT                    = 100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // MUY IMPORTANTE: inflamos el layout el cual representara a este fragmento
        View rootView = inflater.inflate(R.layout.activity_event_list, container, false);


        lvEvents = (ListView) rootView.findViewById(R.id.lv_allEvents);

        // Obtenemos los argumentos en dado caso que se pasaran a este fragmento
        //..
        if(getArguments() == null)
            category = null;
        else
            category = getArguments().getString("category");

        mEventList = new ArrayList<>();

        initListViewEvents();

        EventCRUD eventCRUD = new EventCRUD(getContext());
        List<Event> events;

        //TODO: Hacer una funcion en el CRUD que traiga los eventos por categoria

        switch (category){
            case CATEGORY_FOLLOWING:
                events = eventCRUD.readEvents(1);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_ALL:
                events = eventCRUD.readEvents();
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_ART_AND_CULTURE:
                events = eventCRUD.readEvents(CATEGORY_ART_AND_CULTURE);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_MUSIC:
                events = eventCRUD.readEvents(CATEGORY_MUSIC);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_SCIENCE_AND_TECNOLOGY:
                events = eventCRUD.readEvents(CATEGORY_SCIENCE_AND_TECNOLOGY);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_THEATER:
                events = eventCRUD.readEvents(CATEGORY_THEATER);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            case CATEGORY_SPORTS:
                events = eventCRUD.readEvents(CATEGORY_SPORTS);
                for (Event event : events) {
                    mEventList.add(event);
                }
                break;
            default:
                break;
        }
        updateListView();

        // Actualizamos el list view con el nuevo contacto


        // Limpiamos los "EditText"


        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Event e = mEventList.get(i);
                Intent intent = new Intent(getActivity(), CompleteEventActivity.class);
                intent.putExtra("id", e.getId());
                intent.putExtra("name", e.getName());
                intent.putExtra("description", e.getDescription());
                intent.putExtra("img", e.getImgPath());
                intent.putExtra("cover", e.getCoverPath());
                intent.putExtra("date", e.getDate().getTime());
                intent.putExtra("address", e.getAddress());
                intent.putExtra("price", e.getPrice());
                intent.putExtra("rating", e.getRating());
                intent.putExtra("category", e.getCategory());
                intent.putExtra("following", e.getFollowing());

                startActivityForResult(intent, COMPLETE_EVENT);
            }
        });


        //updateListView();
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EventListActivity.COMPLETE_EVENT && resultCode == RESULT_OK){

            if(category == CATEGORY_FOLLOWING) {
                int event = data.getIntExtra("followEvent", 0);
                int id = data.getIntExtra("id", 0);
                if (event == CompleteEventActivity.EVENT_FOLLOW || event == CompleteEventActivity.EVENT_UNFOLLOW) {
                    updateEvent(id);
                }
            }
        }
    }


    private void initListViewEvents() {
        EventAdapter adapter = new EventAdapter(mEventList);
        lvEvents.setAdapter(adapter);
    }

    private void updateListView() {
        ((EventAdapter)lvEvents.getAdapter()).notifyDataSetChanged();
    }

    public void updateEvent(int id){

        Event target = null;
        for( Event e : mEventList){
            if(e.getId() == id)
                target = e;
        }

        if(target != null){
            mEventList.remove(target);
        }else{
            target = new EventCRUD(getContext()).readEvent(id);
            mEventList.add(target);
        }
        updateListView();
    }


}
