package com.example.kukuliner.kuliner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kukuliner.kuliner.user.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class lupaPassword2 extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        final String Email = b.getString("email");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        Button button = findViewById(R.id.button2);
        final EditText jawaban = findViewById(R.id.jawaban);
        final TextView pertanyaan =findViewById(R.id.pertanyaan);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(Email.equals(ds.child("email").getValue().toString())){
                        String pertanyaanRahasia = ds.child("pertanyaanRahasia").getValue().toString();
                        pertanyaan.setText(pertanyaanRahasia);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            boolean checker = false;
            @Override
            public void onClick(View view) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String answer = jawaban.getText().toString();
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            String jawaban = ds.child("jawaban").getValue().toString();
                            Log.d("lupa", jawaban+" ,"+answer);
                            if(Email.equals(ds.child("email").getValue().toString()) && jawaban.equals(answer)){
                                Intent intent = new Intent(lupaPassword2.this,lupaPassword3.class);
                                intent.putExtra("email",Email);
                                startActivity(intent);
                                finish();
                                checker = true;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}