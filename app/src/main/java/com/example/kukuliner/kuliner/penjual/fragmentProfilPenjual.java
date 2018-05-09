package com.example.kukuliner.kuliner.penjual;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user_drawer;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;

import static com.facebook.FacebookSdk.getApplicationContext;

public class fragmentProfilPenjual extends Fragment {
    TextView username,email,nohp,alamat;
    ImageView userProfil;
    String Username,Email,NoHP,Alamat;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Button share,share2;
    String url;



    com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();

            if(ShareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.penjual_profil_static,container,false);

        username = rootView.findViewById(R.id.penjual_username_profil_static);
        email = rootView.findViewById(R.id.penjual_email_profil_static);
        nohp = rootView.findViewById(R.id.penjual_nohp_profil_static);
        alamat = rootView.findViewById(R.id.penjual_alamat_profil_static);
        userProfil = rootView.findViewById(R.id.penjual_photo_profil_static);
        share = rootView.findViewById(R.id.shareOnFacebookButton);
        share2 = rootView.findViewById(R.id.shareOnFacebookButton2);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("penjual");
        user_drawer activity = (user_drawer) getActivity();
        Username = activity.getUsername();
        Email = activity.getEmail();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile").child(Username);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
            }
        });
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(Email)){
                        NoHP = ds.child("noHP").getValue().toString();
                        Alamat = ds.child("Alamat").getValue().toString();
                        email.setText(Email);
                        username.setText(Username);
                        nohp.setText(NoHP);
                        alamat.setText(Alamat);
                        GlideApp.with(getActivity()).load(mStorageRef).apply(RequestOptions.circleCropTransform()).into(userProfil);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                          if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("projectapps.xyz"))
                            .build();
                    shareDialog.show(linkContent);  // Show facebook ShareDialog
                }
            }
        });

        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getActivity()).load(url).into(target);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}