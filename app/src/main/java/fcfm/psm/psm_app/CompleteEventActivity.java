package fcfm.psm.psm_app;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.List;

import fcfm.psm.psm_app.Database.EventCRUD;
import fcfm.psm.psm_app.Model.Event;

public class CompleteEventActivity extends AppCompatActivity {

    Event event;

    //Head
    ImageView img_eventPic;
    ImageView img_eventCover;
    TextView tv_eventName;
    TextView tv_miniDate;
    RatingBar ratingBar;

    //Footer
    TextView tv_description;
    TextView tv_eventDate;
    TextView tv_eventPrice;
    TextView tv_eventAddress;

    ImageButton btn_openChat;
    ImageButton btn_shareMom;
    ImageButton btn_maps;
    ImageButton btn_follow;

    final int SHARE = 100;

    int followEvent = 0;

    final public static int NO_EVENT = 0;
    final public static int EVENT_FOLLOW = 1;
    final public static int EVENT_UNFOLLOW = 2;

    GestureLibrary gestureLibrary;
    GestureDetector gestureDetector;
    GestureOverlayView gestureOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_event);

        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener());
        gestureOverlayView = (GestureOverlayView)findViewById(R.id.gesture_overlay);

        if(!gestureLibrary.load()){
            Log.e("Gestures", "Los gestures no se cargaron");
        }

        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
//Le pedimos a la libreria que reconozca la gesture que hizo el usuario
                List<Prediction> predictions = gestureLibrary.recognize(gesture);

                if(predictions.size() >  0){

                    Prediction prediction = predictions.get(0);

                    if(prediction.score > 1.0){
                        String name = prediction.name;

                        if(name.equals("follow")) {
                            if(event.getFollowing() == 0) {
                                new EventCRUD(CompleteEventActivity.this).follow(event.getId());
                                showToast("Now you follow this event");
                                followEvent = EVENT_FOLLOW;
                                event.setFollowing(1);
                                btn_follow.setBackgroundColor(Color.parseColor("#f0c23b"));
                            }else{
                                new EventCRUD(CompleteEventActivity.this).unfollow(event.getId());
                                showToast("You no longer follow this event");
                                followEvent = EVENT_UNFOLLOW;
                                event.setFollowing(0);
                                btn_follow.setBackgroundColor(Color.parseColor("#424242"));
                            }
                        }
                    }
                }
            }
        });

        img_eventPic    = (ImageView) findViewById(R.id.img_eventPic);
        img_eventCover  = (ImageView) findViewById(R.id.img_eventCover);
        tv_eventName    = (TextView)  findViewById(R.id.tv_eventName);
        tv_miniDate     = (TextView)  findViewById(R.id.tv_date);
        ratingBar       = (RatingBar) findViewById(R.id.ratingBar);

        tv_description  = (TextView) findViewById(R.id.tv_description);
        tv_eventDate    = (TextView) findViewById(R.id.tv_eventDate);
        tv_eventPrice   = (TextView) findViewById(R.id.tv_eventPrice);
        tv_eventAddress = (TextView) findViewById(R.id.tv_eventAddress);

        btn_maps        = (ImageButton) findViewById(R.id.btn_maps);
        btn_openChat    = (ImageButton) findViewById(R.id.btn_openChat);
        btn_shareMom    = (ImageButton) findViewById(R.id.btn_shareMom);
        btn_follow      = (ImageButton)      findViewById(R.id.btn_follow);

        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(CompleteEventActivity.this, MapsActivity.class);
                maps.putExtra("address", event.getAddress());
                maps.putExtra("name", event.getName());
                startActivity(maps);
            }
        });

        btn_openChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(CompleteEventActivity.this, ChatActivity.class);
                startActivity(chat);
            }
        });
        btn_shareMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(CompleteEventActivity.this, ShareMomentActivity.class);
                share.putExtra("img", event.getCoverPath());
                startActivityForResult(share,SHARE);
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(event.getFollowing() == 0) {
                    new EventCRUD(CompleteEventActivity.this).follow(event.getId());
                    showToast("Now you follow this event");
                    followEvent = EVENT_FOLLOW;
                    event.setFollowing(1);
                    btn_follow.setBackgroundColor(Color.parseColor("#f0c23b"));
                }else{
                    new EventCRUD(CompleteEventActivity.this).unfollow(event.getId());
                    showToast("You no longer follow this event");
                    followEvent = EVENT_UNFOLLOW;
                    event.setFollowing(0);
                    btn_follow.setBackgroundColor(Color.parseColor("#424242"));
                }
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String imgPath = intent.getStringExtra("img");
        String coverPath = intent.getStringExtra("cover");
        Date date = new Date( intent.getLongExtra("date", -1));
        String address = intent.getStringExtra("address");
        float price = intent.getFloatExtra("price", 0);
        float rating = intent.getFloatExtra("rating", 0);
        String category = intent.getStringExtra("category");
        int following = intent.getIntExtra("following", 0);
        event = new Event(id, name, description, date, address, price, imgPath, coverPath, rating,category, following);

        tv_eventName.setText(event.getName());
        tv_description.setText(event.getDescription());
        tv_eventAddress.setText(event.getAddress());
        tv_eventDate.setText(event.getDate().toString());
        tv_miniDate.setText(event.getDate().toString());

        tv_eventPrice.setText("$" + event.getPrice());

        ratingBar.setRating(event.getRating());

        Picasso.with(this).load(event.getImgPath()).into(img_eventPic);
        Picasso.with(this).load(event.getCoverPath()).into(img_eventCover);

        if(event.getFollowing() == 1){
            btn_follow.setBackgroundColor(Color.parseColor("#f0c23b"));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SHARE && resultCode == RESULT_OK){
            showToast("You shared this event");
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("id", event.getId());
        data.putExtra("followEvent", followEvent);
        setResult(RESULT_OK, data);
        finish();
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
