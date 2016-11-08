package fcfm.psm.psm_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fcfm.psm.psm_app.Database.EventCRUD;

/**
 * Created by Luis Aldrian on 08/11/2016.
 */

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView)findViewById(R.id.calendar);
        actionBar.setTitle(dateFormatMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));


        EventCRUD eventCRUD = new EventCRUD(this);
        List<fcfm.psm.psm_app.Model.Event> events = eventCRUD.readEvents();

        for(fcfm.psm.psm_app.Model.Event e : events){
            Event calendarEvent;
            if(e.getFollowing() == 1) {
                calendarEvent = new Event(Color.GREEN, e.getDate().getTime(), e.getId());
            }else{
                calendarEvent = new Event(Color.RED, e.getDate().getTime(), e.getId());
            }
            compactCalendar.addEvent(calendarEvent);
        }

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        //List<Event> events = compactCalendar.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
        Log.d("LOL", "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Log.d("LEL", "Day was clicked: " + dateClicked + " with events " + events);
                if(events != null & events.size() > 0) {
                    int id = (int) events.get(0).getData();
                    fcfm.psm.psm_app.Model.Event e = new EventCRUD(CalendarActivity.this).readEvent(id);
                    if(e != null){
                        Intent intent = new Intent(CalendarActivity.this, CompleteEventActivity.class);
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

                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                Log.d("lol", "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

        //cv_calendar.
    }
}
