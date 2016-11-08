package fcfm.psm.psm_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fcfm.psm.psm_app.Database.EventCRUD;

public class LoginActivity extends AppCompatActivity {

    Button btn_starWfb;


    final int MAIN_ACTIVITY_REQUEST = 1;

    CallbackManager callbackManager;

    GestureLibrary gestureLibrary;
    GestureDetector gestureDetector;
    GestureOverlayView gestureOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(LoginActivity.this);
        AppEventsLogger.activateApp(this);

        init();
    }

    public void init(){
        btn_starWfb = (Button)findViewById(R.id.btn_startWfb);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "fcfm.psm.psm_app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();
        if(profile != null) {
            Log.e("Profile Id, Name", "" + profile.getId() + " " + profile.getName());
        }
        if(accessToken != null) {
            if( !accessToken.isExpired()) {
                openMainActivity();
            }
        }
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        showToast("Logged with facebook");
                        openMainActivity();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                    }
                });

        btn_starWfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });


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
                            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                        }
                    }
                }
            }
        });

    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void openMainActivity(){
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_ACTIVITY_REQUEST) {
        }
    }
}

