package com.example.kukuliner.kuliner.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kukuliner.kuliner.Model.userProgress;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.userAdapter2;
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

public class cekPesananActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<userProgress> data = new ArrayList<>();
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    String namaMakanan,hargaMakanan,jmlMakanan,namaToko,alamat,status,pembeli;
    String username,email,tipe;
    userAdapter2 adapter2;
    Button button;
    StorageReference img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_cek_pesanan);

        Intent intent= getIntent();
        user_drawer activity = new user_drawer();
        Bundle b = intent.getExtras();
        username = b.getString("user_username");
        email = b.getString("user_email");
        tipe     = b.getString("tipe");
        button = findViewById(R.id.backToHome);
        recyclerView  = findViewById(R.id.recycleview_cek_pesanan_user);
        layoutManager = new LinearLayoutManager(cekPesananActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter2= new userAdapter2(this,data);
        recyclerView.setAdapter(adapter2);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile").child("menu");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cekPesananActivity.this,user_drawer.class);
                intent.putExtra("user_username",username);
                intent.putExtra("user_email",email);
                intent.putExtra("tipe",tipe);
                startActivity(intent);
                finish();
            }
        });
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    key = ds.getKey();
                    namaToko = ds.child("namaToko").getValue().toString();
                    alamat = ds.child("alamat").getValue().toString();
                    pembeli = ds.child("pembeli").getValue().toString();
                    hargaMakanan = ds.child("hargaMakanan").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    jmlMakanan = ds.child("jmlMakanan").getValue().toString();
                    status = ds.child("status").getValue().toString();
                    img = mStorageRef.child(namaToko).child(namaMakanan);
                    if(pembeli.equals(username) && !status.equals("completed")){
                        Log.d("cek userPesananActivity",hargaMakanan+namaMakanan+namaToko+img.toString());
                        data.add(new userProgress(img,alamat,"Rp. "+hargaMakanan,jmlMakanan+" Porsi",namaMakanan,status,namaToko,pembeli,key));
                    }
                }
                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
