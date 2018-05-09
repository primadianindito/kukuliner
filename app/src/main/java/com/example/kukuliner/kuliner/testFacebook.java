package com.example.kukuliner.kuliner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Share;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class testFacebook extends AppCompatActivity {
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
            if (shareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(testFacebook.this);
        Button button = findViewById(R.id.button4);
        ImageView imageView = findViewById(R.id.imageView2);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        ShareButton shareButton = findViewById(R.id.shareFB);


        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.user);

        SharePhoto photo = new SharePhoto.Builder().setBitmap(icon).build();


        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        shareButton.setShareContent(content);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getBaseContext(),"SUCCESS",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

                Picasso.with(testFacebook.this).load("http://assets1.ignimgs.com/2018/03/23/batman-ninja-nycc-poster-1521767460627_1280w.jpg").into(target);
            }
        });
    }
}
