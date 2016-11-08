package fcfm.psm.psm_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

/**
 * Created by Luis Aldrian on 08/11/2016.
 */

public class CalendarActivity extends AppCompatActivity {

    CalendarView cv_calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        cv_calendar = (CalendarView) findViewById(R.id.cv_calendar);


        //cv_calendar.
    }
}
