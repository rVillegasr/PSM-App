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
    boolean mUseCustomLayout;
    ListView lvEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // MUY IMPORTANTE: inflamos el layout el cual representara a este fragmento
        View rootView = inflater.inflate(R.layout.activity_event_list, container, false);


        lvEvents = (ListView) rootView.findViewById(R.id.lv_allEvents);

        // Obtenemos los argumentos en dado caso que se pasaran a este fragmento
        //..

        if(getArguments()==null)
            mUseCustomLayout = false;
        else
            mUseCustomLayout = getArguments().getBoolean("useCustomLayout", false);
        //En caso de que no encuentre la llave ponle el segundo valor

        mEventList = new ArrayList<>();



        // Esta es una forma de obtener un objeto tipo "Bitmap" de un "ImageView"
        //BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPicture.getDrawable();
        // Creamos y agregamos nuestro nuevo contacto
        Event p = new Event("Musical",null, null);
        Event p2 = new Event("Teatro",null, null);
        Event p3 = new Event("cine",null, null);
        Event p4 = new Event("cosa",null, null);
        mEventList.add(p);
        mEventList.add(p2);
        mEventList.add(p3);
        mEventList.add(p4);


        // Actualizamos el list view con el nuevo contacto


        // Limpiamos los "EditText"


        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(EventListActivity.this.getActivity(), CompleteEventActivity.class);

                startActivity(myIntent);
            }
        });


        // Inicializamos por primera vez el listview de los contactos
        //..

        initListViewContacts();

        //updateListViewContacts();
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Previamente especificamos que 1 es para el intent de la camara
        // y si el "resultCode" es OK tenemos la imagen tomada por el usuario
    }


    private void initListViewContacts() {
        // Si no usaremos un "CustomLayout" usamos por defecto un ArrayAdapter
        // MUY IMPORTANTE: Este "ArrayAdapter" que estamos creando solo mostrara un texto
        // por lo tanto si le pasamos un objeto que no sea de tipo "String" lo que va a hacer el adaptador
        // es ir al objeto y ejecutar el metodo "toString()" que en JAVA todos los objetos tienen y si ese metodo
        // no lo sobrecargamos tendriamos la direccion del objeto como si fuera un "texto".
        // Mas info en clase y en el modelo "Person"


        if(mUseCustomLayout){
            EventAdapter adapter = new EventAdapter(mEventList);
            lvEvents.setAdapter(adapter);
        }
        else {
            ArrayAdapter<Event> adapter;
            adapter = new ArrayAdapter<Event>(getActivity(), android.R.layout.simple_list_item_1, mEventList);
            lvEvents.setAdapter(adapter);
        }
    }

    private void updateListViewContacts() {
        // El metodo "notifyDataSetChanged" sirve para indicarle al adaptador que los elementos que esta "controlando"
        // han cambiado o bien se a agregado/eliminado un elemento
        //..
        //Si estamos usando el custom layout se castea a Contact y si no a ArrayAdapter en el notifySetCahnged
        if(mUseCustomLayout){
            ((EventAdapter)lvEvents.getAdapter()).notifyDataSetChanged();
        }
        else {
            ((ArrayAdapter) lvEvents.getAdapter()).notifyDataSetChanged();
        }
    }




}
