package fcfm.psm.psm_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button btn_maps;

    final int SHARE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_event);

        img_eventPic    = (ImageView) findViewById(R.id.img_eventPic);
        img_eventCover  = (ImageView) findViewById(R.id.img_eventCover);
        tv_eventName    = (TextView) findViewById(R.id.tv_eventName);
        tv_miniDate     = (TextView) findViewById(R.id.tv_date);
        ratingBar       = (RatingBar)findViewById(R.id.ratingBar);

        tv_description  = (TextView) findViewById(R.id.tv_description);
        tv_eventDate    = (TextView) findViewById(R.id.tv_eventDate);
        tv_eventPrice   = (TextView) findViewById(R.id.tv_eventPrice);
        tv_eventAddress = (TextView) findViewById(R.id.tv_eventAddress);

        btn_maps        = (Button) findViewById(R.id.btn_maps);
        btn_openChat    = (ImageButton) findViewById(R.id.btn_openChat);
        btn_shareMom    = (ImageButton) findViewById(R.id.btn_shareMom);

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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                /*
                * TODO: Web service rating
                * */
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
        event = new Event(id, name, description, date, address, price, imgPath, coverPath, rating);

        tv_eventName.setText(event.getName());
        tv_description.setText(event.getDescription());
        tv_eventAddress.setText(event.getAddress());
        tv_eventDate.setText(event.getDate().toString());
        tv_miniDate.setText(event.getDate().toString());

        tv_eventPrice.setText("$" + event.getPrice());

        ratingBar.setRating(event.getRating());

        Picasso.with(this).load(event.getImgPath()).into(img_eventPic);
        Picasso.with(this).load(event.getCoverPath()).into(img_eventCover);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SHARE && resultCode == RESULT_OK){
            showToast("You shared this event");
        }
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
