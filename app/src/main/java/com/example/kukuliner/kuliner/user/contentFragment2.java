package com.example.kukuliner.kuliner.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.Model.userTab2;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.userAdapter1;
import com.example.kukuliner.kuliner.adapter.userAdapter3;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class contentFragment2 extends Fragment {
    private RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    private List<userTab1> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    String namaToko,alamat,pembeli,username;
    userAdapter1 adapter1;
    int ratting;
    StorageReference img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_content, container,    false);
        recyclerView  = rootView.findViewById(R.id.recycleview_content_user);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new userAdapter1(getContext(),data);
        recyclerView.setAdapter(adapter1);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("penjual");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    user_drawer activity = (user_drawer) getActivity();
                    pembeli = activity.getUsername();
                    namaToko = ds.child("name").getValue().toString();
                    alamat = ds.child("Alamat").getValue().toString();
                    username = ds.child("name").getValue().toString();
                    status = ds.child("aktivasi").getValue().toString();
                    img = mStorageRef.child(username);
                    ratting = Integer.valueOf(ds.child("ratting").getValue().toString()) ;
                    if(status.equals("yes")){
                        data.add(new userTab1(namaToko,img,alamat,ratting,pembeli));
                    }

                }
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}