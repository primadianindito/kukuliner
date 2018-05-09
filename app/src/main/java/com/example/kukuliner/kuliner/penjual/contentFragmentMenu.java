package com.example.kukuliner.kuliner.penjual;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kukuliner.kuliner.Model.penjualMenu;
import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.userTab1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.penjualAdapter1;
import com.example.kukuliner.kuliner.adapter.penjualMenuAdapter;
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

public class contentFragmentMenu extends Fragment {

    //Deklarasi datanya
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<penjualMenu> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    penjualMenuAdapter adapter1;
    String username,hargaMakanan,namaMakanan,namaToko;
    Button button;
    StorageReference img;

    @Nullable
    @Override
    //Untuk mengganti layout yang tadinya kodingan jadi gambar
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.penjual_menu, container,    false);

        //inisiasi data-datanya
        recyclerView  = rootView.findViewById(R.id.recycleview_menu_penjual);
        layoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new penjualMenuAdapter(getContext(),data);
        recyclerView.setAdapter(adapter1);
        user_drawer activity = (user_drawer) getActivity();
        username = activity.getUsername();

        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile").child("menu");
        button = rootView.findViewById(R.id.tambah_pesanan);

        //ketika klik buttonnya, maka akan menambah pesanan
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTambahMenu nextFrag= new fragmentTambahMenu();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, nextFrag,"tambah pesanan")
                        .addToBackStack(null)
                        .commit();
            }
        });
        //pengembalian nilai untuk layoutnya
        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inisiasi database untuk firebasenya
        mDatabase = FirebaseDatabase.getInstance().getReference().child("menu");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            // 	Membaca dan mendeteksi perubahan pada seluruh konten di sebuah lokasi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    hargaMakanan = ds.child("harga").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    namaToko = ds.child("namaToko").getValue().toString();
                    img = mStorageRef.child(username).child(namaMakanan);
                    Log.d("cek menu", "data = " + namaMakanan + "," + hargaMakanan + ", " + img+", "+username);
                    if(namaToko.equals(username)){
                        data.add(new penjualMenu(img, namaMakanan, hargaMakanan, namaToko));
                    }
                }
                //untuk mengambil / melihat data dari adapter
            adapter1.notifyDataSetChanged();
            }
            //Jika database erorr / data erorr maka tidak masuk ke databasenya
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}