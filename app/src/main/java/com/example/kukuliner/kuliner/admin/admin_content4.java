package com.example.kukuliner.kuliner.admin;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kukuliner.kuliner.Model.adminTab3;
import com.example.kukuliner.kuliner.Model.feedback1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.adminAdapter3;
import com.example.kukuliner.kuliner.adapter.adminAdapter4;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class admin_content4 extends Fragment {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<feedback1> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    adminAdapter4 adapter1;
    String nama, feedback;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin_content_4,container,false);
        recyclerView  = rootView.findViewById(R.id.admin_tab4);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new adminAdapter4(getContext(),data);
        recyclerView.setAdapter(adapter1);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("feedback");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        nama = ds.child("username").getValue().toString();
                        feedback = ds.child("text").getValue().toString();
                            data.add(new feedback1(feedback,nama));
                    }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}