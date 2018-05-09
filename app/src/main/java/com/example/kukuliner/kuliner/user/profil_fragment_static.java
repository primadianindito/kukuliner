package com.example.kukuliner.kuliner.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class profil_fragment_static extends Fragment {
    TextView username,email,nohp,alamat;
    ImageView userProfil;
    String Username,Email,NoHP,Alamat,url;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    Button share,share2;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

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

    @Override
    public void onResume() {
        super.onResume();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profil_static,container,false);
        username = rootView.findViewById(R.id.user_username_profil_static);
        email = rootView.findViewById(R.id.user_email_profil_static);
        nohp = rootView.findViewById(R.id.user_nohp_profil_static);
        alamat = rootView.findViewById(R.id.user_alamat_profil_static);
        userProfil = rootView.findViewById(R.id.user_photo_profil_static);
        share = rootView.findViewById(R.id.shareOnFacebookButton);
        share2 = rootView.findViewById(R.id.shareOnFacebookButton2);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        user_drawer activity = (user_drawer) getActivity();
        Username = activity.getUsername();
        Email = activity.getEmail();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile").child(Username);
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("lipsum.com"))
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
}