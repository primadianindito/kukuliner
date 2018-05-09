package com.example.kukuliner.kuliner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kukuliner.kuliner.user.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class lupaPassword3 extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_3);
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        final String Email = b.getString("email");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        Button button = findViewById(R.id.submit_password);
        final EditText password = findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password2 = password.getText().toString();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            if(Email.equals(ds.child("email").getValue().toString())){
                                String key = ds.getKey();
                                mDatabase.child(key).child("password").setValue(password2);
                                Intent intent = new Intent(lupaPassword3.this,login.class);
                                startActivity(intent);
                                finish();
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