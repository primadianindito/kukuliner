package com.example.kukuliner.kuliner.user;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.admin.admin;
import com.example.kukuliner.kuliner.lupaPassword;
import com.example.kukuliner.kuliner.penjual.register2;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import bolts.Task;

public class login extends AppCompatActivity {
    EditText email,password;
    TextView register,registerPenjual,lupa;
    Button signin;
    String tipe;
    boolean checkerUser = false, checkerAdmin = false, checkerPenjual = false, checkerLogin = false;
    Fragment fragment;
    String Email,Password,username;
    private DatabaseReference mDatabase,mDatabase2,mDatabase3;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDialog = new ProgressDialog(login.this);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        register = findViewById(R.id.klikDisini);
        registerPenjual =findViewById(R.id.klikwarung);
        signin   = findViewById(R.id.signIn_button);
        lupa = findViewById(R.id.klik_lupa_password);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Admin");
        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("penjual");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        registerPenjual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register2.class);
                startActivity(intent);
            }
        });
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, lupaPassword.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.setMessage("Login.....");
                mDialog.show();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Log.d("cek","user = "+mDatabase);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        if(checkerLogin==true){
                            Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            checkerLogin=false;
                        }else{
                            Toast.makeText(login.this, "Username / Password anda salah", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                }, 2000);

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange (DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            Log.d("cek","user = "+username);
                            if(ds.child("email").getValue().equals(Email)&& ds.child("password").getValue().equals(Password) && ds.child("tipe").getValue().equals("user")){
                                checkerLogin = true;
                                Intent intent = new Intent(login.this, user_drawer.class);
                                intent.putExtra("user_email",Email);
                                username = ds.child("username").getValue().toString();
                                String tipe = ds.child("tipe").getValue().toString();
                                intent.putExtra("user_username",username);
                                intent.putExtra("tipe",tipe);
                                Log.d("cek","user = "+username);
                                startActivity(intent);
                                mDialog.dismiss();
                            }else{
                                Log.d("cek","Gak ada user");
                            }
                            checkerUser = false;
                        }

                        checkerUser = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

                mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            if( ds.child("email").getValue().equals(Email)&& ds.child("password").getValue().equals(Password) && ds.child("tipe").getValue().equals("admin")){
                                checkerLogin = true;
                                Intent intent = new Intent(login.this, admin.class);
                                intent.putExtra("user_email",Email);
                                String username = ds.child("username").getValue().toString();
                                intent.putExtra("user_username",username);
                                Log.d("cek","user = "+username);
                                startActivity(intent);
                                mDialog.dismiss();
                            }
                            checkerAdmin = false;
                        }
                        checkerAdmin = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            if(ds.child("email").getValue().equals(Email)&& ds.child("password").getValue().equals(Password) && ds.child("tipe").getValue().equals("penjual") && ds.child("aktivasi").getValue().equals("yes")){
                                checkerLogin = true;
                                Intent intent = new Intent(login.this, user_drawer.class);
                                intent.putExtra("user_email",Email);
                                username = ds.child("name").getValue().toString();
                                String tipe = ds.child("tipe").getValue().toString();
                                intent.putExtra("user_username",username);
                                intent.putExtra("tipe",tipe);
                                Log.d("cek","user = "+username);
                                startActivity(intent);
                                mDialog.dismiss();
                            }else{
                                Log.d("cek","Gak ada user");
                            }
                            checkerPenjual = false;
                        }
                        checkerPenjual = true;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });


    }
}