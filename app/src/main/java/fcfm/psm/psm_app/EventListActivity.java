package fcfm.psm.psm_app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fcfm.psm.psm_app.Adapter.EventAdapter;
import fcfm.psm.psm_app.Model.Event;

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

        // Esta es una forma de obtener un objeto tipo "Bitmap" de un "ImageView"
        //BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPicture.getDrawable();
        // Creamos y agregamos nuestro nuevo contacto


        if(showAllEvents){
            /*
            * TODO: Web service, obtener todos los eventos
            * */
            Event p = new Event("Musical",null, null);
            Event p2 = new Event("Teatro",null, null);
            Event p3 = new Event("cine",null, null);
            Event p4 = new Event("cosa",null, null);
            Event p5 = new Event("5",null, null);
            mEventList.add(p);
            mEventList.add(p2);
            mEventList.add(p3);
            mEventList.add(p4);
            mEventList.add(p5);
        }else{
            /*
            * TODO: Web service los eventos proximos
            * */
            Event p = new Event("Musical",null, null);
            Event p2 = new Event("Teatro",null, null);
            Event p5 = new Event("5",null, null);
            mEventList.add(p);
            mEventList.add(p2);
            mEventList.add(p5);
        }




        // Actualizamos el list view con el nuevo contacto


        // Limpiamos los "EditText"


        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent eventActivity = new Intent(EventListActivity.this.getActivity(), CompleteEventActivity.class);
                eventActivity.putExtra("name",mEventList.get(i).getName());
                eventActivity.putExtra("description",mEventList.get(i).getDescription());
                eventActivity.putExtra("img",mEventList.get(i).getImg());
                eventActivity.putExtra("cover",mEventList.get(i).getCover());
                startActivity(eventActivity);
            }
        });


        // Inicializamos por primera vez el listview de los contactos
        //..

        initListViewContacts();

        //updateListView();
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Previamente especificamos que 1 es para el intent de la camara
        // y si el "resultCode" es OK tenemos la imagen tomada por el usuario
    }


    private void initListViewContacts() {
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
