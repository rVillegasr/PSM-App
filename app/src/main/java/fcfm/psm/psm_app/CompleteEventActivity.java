package fcfm.psm.psm_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fcfm.psm.psm_app.Model.Event;

public class CompleteEventActivity extends AppCompatActivity {

    Event event;

    //Head
    ImageView img_eventPic;
    ImageView img_eventCover;
    TextView tv_eventName;

    //Footer
    TextView tv_description;
    TextView tv_eventDate;
    TextView tv_eventPrice;
    TextView tv_eventAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_event);

        img_eventPic = (ImageView) findViewById(R.id.img_eventPic);
        img_eventCover = (ImageView) findViewById(R.id.img_eventCover);
        tv_eventName = (TextView) findViewById(R.id.tv_eventName);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_eventDate = (TextView) findViewById(R.id.tv_eventDate);
        tv_eventPrice = (TextView) findViewById(R.id.tv_eventPrice);
        tv_eventAddress = (TextView) findViewById(R.id.tv_eventAddress);

        /*
        *       eventActivity.putExtra("name",mEventList.get(i).getName());
                eventActivity.putExtra("description",mEventList.get(i).getDescription());
                eventActivity.putExtra("img",mEventList.get(i).getImg());
                eventActivity.putExtra("cover",mEventList.get(i).getCover());
         */

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        Bitmap pic = (Bitmap)intent.getParcelableExtra("img");
        Bitmap cover = (Bitmap)intent.getParcelableExtra("cover");
        //event = new Event(name, description, pic, cover);

        tv_eventName.setText(event.getName());
        tv_description.setText(event.getDescription());
    }
}
