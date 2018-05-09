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

import com.example.kukuliner.kuliner.Model.penjualTab1;
import com.example.kukuliner.kuliner.Model.penjualTab2;
import com.example.kukuliner.kuliner.Model.penjualTab3;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.adapter.penjualAdapter1;
import com.example.kukuliner.kuliner.adapter.penjualAdapter2;
import com.example.kukuliner.kuliner.adapter.penjualAdapter3;
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

public class contentFragmentPenjual2 extends Fragment {

    //Deklarasi datanya
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private List<penjualTab2> data;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    penjualAdapter2 adapter1;
    String namaToko,namaMakanan,progress,pembeli,username,alamat,porsi,harga;
    StorageReference img;
    @Nullable
    @Override

    //Untuk mengganti layout yang tadinya kodingan jadi gambar
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.penjual_content_2, container,    false);

        //inisiasi data-datanya
        recyclerView  = rootView.findViewById(R.id.recycleview_content_penjual2);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new penjualAdapter2(getContext(),data);
        recyclerView.setAdapter(adapter1);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inisiasi database untuk firebasenya
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pesanan");
        data = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            // 	Membaca dan mendeteksi perubahan pada seluruh konten di sebuah lokasi
            public void onDataChange(DataSnapshot dataSnapshot) {
                int intporsi,intharga;
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    user_drawer activity = (user_drawer) getActivity();
                    username = activity.getUsername();
                    namaToko = ds.child("namaToko").getValue().toString();
                    namaMakanan = ds.child("namaMakanan").getValue().toString();
                    progress = ds.child("status").getValue().toString();
                    pembeli = ds.child("pembeli").getValue().toString();
                    alamat = ds.child("alamat").getValue().toString();
                    harga = ds.child("hargaMakanan").getValue().toString();
                    porsi = ds.child("jmlMakanan").getValue().toString();
                    intporsi = Integer.parseInt(porsi);
                    intharga = Integer.parseInt(harga);
                    img = mStorageRef.child(pembeli);
                    Log.d("cek penjual2", "data = "+namaToko);
                    if(username.equals(namaToko) && progress.equals("on progress")){
                        data.add(new penjualTab2(pembeli,img,namaMakanan,porsi+" porsi/ Rp. "+(intharga*intporsi),progress,alamat));
                    }
                    //untuk mengambil / melihat data dari adapter
                }adapter1.notifyDataSetChanged();
            }
            //Jika database erorr / data erorr maka tidak masuk ke databasenya
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}