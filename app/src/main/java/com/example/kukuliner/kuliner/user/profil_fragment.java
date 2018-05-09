package com.example.kukuliner.kuliner.user;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.penjual.contentFragmentMenu;
import com.example.kukuliner.kuliner.penjual.fragmentProfilPenjual;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class profil_fragment extends Fragment {
    Context context;
    private StorageReference mStorageRef;
    private DatabaseReference mDataf;
    ImageView drawerImage,profilImage,AppbarImage;
    TextView uploadPicture;
    String i;
    View rootView;
    String username,email,tipe;
    private ProgressDialog mProgress;
    FragmentTransaction fragmentManager;
    NavigationView navigationView;
    public final static int GALLERY_INTENT = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_profil,container,false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadPicture = rootView.findViewById(R.id.user_edit_upload_foto);
        user_drawer activity = (user_drawer) getActivity();
        username = activity.getUsername();
        email    = activity.getEmail();
        tipe     = activity.getTipe();
        drawerImage = rootView.findViewById(R.id.user_image);
        profilImage = rootView.findViewById(R.id.user_photo_profil_edit);
        AppbarImage = rootView.findViewById(R.id.htab_header);
        Log.d("cek profile",email);
        mStorageRef = mStorageRef.child("Profile").child(username);
        GlideApp.with(getActivity()).load(mStorageRef).apply(RequestOptions.circleCropTransform()).into(profilImage);
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        drawerImage = header.findViewById(R.id.user_image);
        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
                mProgress = new ProgressDialog(getActivity());
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("cek","CODE = "+requestCode);
        if(requestCode  == GALLERY_INTENT){
            mProgress.setMessage("UPLOADING.....");
            mProgress.show();
            Uri uri = data.getData();
            StorageReference filePath = mStorageRef;
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(),"UPLOAD DONE",Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    Uri downloadProfilPicture = taskSnapshot.getDownloadUrl();
                    Glide.with(getActivity()).load(downloadProfilPicture).apply(RequestOptions.circleCropTransform()).into(profilImage);
                    GlideApp.with(getActivity()).load(mStorageRef).apply(RequestOptions.circleCropTransform()).into(drawerImage);
                    Intent intent = new Intent(getActivity(), user_drawer.class);
                }
            });
        }
    }
}
