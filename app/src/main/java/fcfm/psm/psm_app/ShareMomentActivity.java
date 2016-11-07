package fcfm.psm.psm_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ShareMomentActivity extends AppCompatActivity {

    Button btn_share;
    ImageButton btn_camera;
    ImageButton btn_memory;
    TextView txt_postDescription;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    MessageDialog messageDialog;

    ImageView img_selectedImg;
    Bitmap sharedPicture;
    String imgUrl;

    private static final int SELECT_PICTURE = 100;
    private static final int TAKE_PICTURE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_moment);

        btn_share = (Button)findViewById(R.id.btn_share);
        btn_camera = (ImageButton)findViewById(R.id.btn_camera);
        btn_memory = (ImageButton)findViewById(R.id.btn_memory);
        txt_postDescription = (TextView)findViewById(R.id.txt_postDescription);
        img_selectedImg = (ImageView)findViewById(R.id.img_selectedImg);

        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("img");


        Picasso.with(this).load(imgUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                sharedPicture = bitmap;
                img_selectedImg.setImageBitmap(sharedPicture);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] permissos = {"android.permission.CAMERA"};

                if (ContextCompat.checkSelfPermission(ShareMomentActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ShareMomentActivity.this, permissos, 1 );

                }else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, TAKE_PICTURE);
                }

            }
        });

        btn_memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pictureIntent = new Intent();
                pictureIntent.setType("image/*");
                pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pictureIntent, "Select picture"), SELECT_PICTURE);
            }
        });


        final FacebookCallback<Sharer.Result> facebookCallback= new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.e("Success", result.toString());
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onCancel() {
                Log.e("Cancel", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        };

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareDialog = new ShareDialog(ShareMomentActivity.this);
                messageDialog = new MessageDialog(ShareMomentActivity.this);
                callbackManager = CallbackManager.Factory.create();
                messageDialog.registerCallback(callbackManager, facebookCallback);
                shareDialog.registerCallback(callbackManager, facebookCallback);

                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(sharedPicture)
                            .build();

                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    //TODO: Agregar un boton para elegir en donde lo quieres compartir
                    
                    shareDialog.show(content);
                    //messageDialog.show(content);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("CAMERA", "CAMERA permission has now been granted. Showing preview.");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
            }

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            if(selectedImageUri != null){
//                    String path = getPathFromURI(selectedImageUri);
//                    Log.i("ImagePath", path);
                img_selectedImg.setImageURI(selectedImageUri);
                sharedPicture = ((BitmapDrawable)img_selectedImg.getDrawable()).getBitmap();
            }
        }
        else if( requestCode == TAKE_PICTURE && resultCode == RESULT_OK){
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                String root = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
                String imagePath = "picture_" + new Date().getTime() + ".jpg";
                File file = new File(root + imagePath);
                if(!file.exists()){
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                }
                img_selectedImg.setImageBitmap(bitmap);
                sharedPicture = bitmap;
            } catch(Exception ex) {
                ex.printStackTrace();
                Toast.makeText(ShareMomentActivity.this, "Problema al regresar de la cámara", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
