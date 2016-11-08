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

public class EventListActivity extends Fragment {
    List<Event> mEventList;
    boolean showAllEvents;
    ListView lvEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // MUY IMPORTANTE: inflamos el layout el cual representara a este fragmento
        View rootView = inflater.inflate(R.layout.activity_event_list, container, false);


        lvEvents = (ListView) rootView.findViewById(R.id.lv_allEvents);

        // Obtenemos los argumentos en dado caso que se pasaran a este fragmento
        //..
        if(getArguments() == null)
            showAllEvents = false;
        else
            showAllEvents = getArguments().getBoolean("all", false);

        mEventList = new ArrayList<>();

        initListViewEvents();

        EventCRUD eventCRUD = new EventCRUD(getContext());
        List<Event> events = eventCRUD.readEvents();
        if(events != null) {
            for (Event event : events) {
                mEventList.add(event);
            }
            updateListView();
        }
        if(showAllEvents){
            /*
            * TODO: Web service, obtener todos los eventos
            * */


        }else{
            /*
            * TODO: Web service los eventos proximos
            * */
        }




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
                startActivity(intent);
            }
        });


        // Inicializamos por primera vez el listview de los contactos
        //..


        //updateListView();
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Previamente especificamos que 1 es para el intent de la camara
        // y si el "resultCode" es OK tenemos la imagen tomada por el usuario
    }


    private void initListViewEvents() {
        EventAdapter adapter = new EventAdapter(mEventList);
        lvEvents.setAdapter(adapter);
    }

    private void updateListView() {
        // El metodo "notifyDataSetChanged" sirve para indicarle al adaptador que los elementos que esta "controlando"
        // han cambiado o bien se a agregado/eliminado un elemento
        //..

        ((EventAdapter)lvEvents.getAdapter()).notifyDataSetChanged();
    }
}
