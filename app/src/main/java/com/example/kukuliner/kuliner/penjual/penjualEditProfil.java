package com.example.kukuliner.kuliner.penjual;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kukuliner.kuliner.GlideApp;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class penjualEditProfil extends Fragment implements OnMapReadyCallback {
    Context context;
    private StorageReference mStorageRef;
    private DatabaseReference mDataf;
    ImageView drawerImage,profilImage,AppbarImage;
    TextView uploadPicture;
    String i;
    GoogleMap map;
    EditText search,name,password,noHP,email,alamat;
    Button searchButton;
    MapView mapView;
    String username,searchText,namaToko,passwordToko,noHPToko,emailToko,alamatToko,EmailUser;
    View rootView;
    LatLng latLng;
    Marker marker;
    double latitude=0,longitude=0;
    private DatabaseReference mDatabase;
    List<Address> adressList;
    Address address;
    private ProgressDialog mProgress;
    public final static int GALLERY_INTENT = 2;
    public penjualEditProfil() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.penjual_profil,container,false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadPicture = rootView.findViewById(R.id.user_edit_upload_foto);
        user_drawer activity = (user_drawer) getActivity();
        username = activity.getUsername();
        EmailUser = activity.getEmail();
        drawerImage = rootView.findViewById(R.id.user_image);
        profilImage = rootView.findViewById(R.id.user_photo_profil_edit);
        AppbarImage = rootView.findViewById(R.id.htab_header);
        searchButton = rootView.findViewById(R.id.search_button);
        name = rootView.findViewById(R.id.user_edit_username);
        password = rootView.findViewById(R.id.user_edit_password);
        noHP = rootView.findViewById(R.id.user_edit_nomorhp);
        email = rootView.findViewById(R.id.user_edit_email);
        alamat = rootView.findViewById(R.id.user_edit_alamat);
        Log.d("cek",username);
        mStorageRef = mStorageRef.child("Profile").child(username);
        GlideApp.with(getActivity()).load(mStorageRef).apply(RequestOptions.circleCropTransform()).into(profilImage);
        latLng = new LatLng(-6.976032, 107.633386);




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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = rootView.findViewById(R.id.maps2);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
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
                }
            });
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
            MapsInitializer.initialize(getContext());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("MUNJUL FOREVER"));
            final CameraPosition munjul = CameraPosition.builder().target(latLng).zoom(16).bearing(90).tilt(30).build();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(munjul));
            searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText = alamat.getText().toString();
                namaToko = name.getText().toString();
                passwordToko = password.getText().toString();
                noHPToko= noHP.getText().toString();
                emailToko = email.getText().toString();
                if(!searchText.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        adressList = geocoder.getFromLocationName(searchText,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    googleMap.clear();
                    if(adressList.size()>0){
                        address = adressList.get(0);
                        latLng = new LatLng(address.getLatitude(),address.getLongitude());
                        latitude = address.getLatitude();
                        longitude = address.getLongitude();
                    }else {
                        Toast.makeText(getActivity(), "Lokasi Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                    }
                    mDataf = FirebaseDatabase.getInstance().getReference().child("penjual");
                    mDataf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String key = ds.getKey();
                                String email = ds.child("email").getValue().toString();
                                if(email.equals(EmailUser)){
                                    mDataf.child(key).child("latitude").setValue(latitude);
                                    mDataf.child(key).child("longitude").setValue(longitude);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(latLng==null){
                        Toast.makeText(getActivity(), "Lokasi Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("cek maps", "onClick: "+latLng);
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("MUNJUL FOREVER"));
                        CameraPosition munjul = CameraPosition.builder().target(latLng).zoom(16).bearing(90).tilt(30).build();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(munjul));
                    }

                }
            }
        });

    }
}