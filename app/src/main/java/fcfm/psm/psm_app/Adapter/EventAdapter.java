package fcfm.psm.psm_app.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import fcfm.psm.psm_app.Model.Event;
import fcfm.psm.psm_app.R;

/**
 * Created by RVR_ on 25/09/2016.
 */
public class EventAdapter extends BaseAdapter{
    List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        Event e = eventList.get(i);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.fragment_event_head, null);
        }
        TextView tv_eventName = (TextView)convertView.findViewById(R.id.tv_eventName);
        /////////Al parecer setImageResource sirve para poner imagenes a los views de imagenes
        //ImageView img_eventPic = (ImageView)convertView.findViewById(R.id.img_eventPic);
        //img_eventPic.setImageResource();
        //ImageView img_eventCover = (ImageView)convertView.findViewById(R.id.img_eventCover);
        //img_eventCover.setImageResource();

        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                /*
                * TODO: Web service de rating
                * */
            }
        });
        ratingBar.setRating(e.getRating());

        tv_eventName.setText(e.getName());

        return convertView;
    }

    @Override
    public Object getItem(int i) {
        return eventList.get(i);
    }
}
