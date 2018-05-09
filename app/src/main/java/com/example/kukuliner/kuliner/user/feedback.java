package com.example.kukuliner.kuliner.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kukuliner.kuliner.Model.feedback1;
import com.example.kukuliner.kuliner.R;
import com.example.kukuliner.kuliner.user_drawer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class feedback extends Fragment {
    private DatabaseReference mDatabase;
    String username;
    Button button;
    EditText feedback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_feedback, container, false);
        user_drawer activity = (user_drawer) getActivity();
        username = activity.getUsername();
        button = rootView.findViewById(R.id.user_feedback_button);
        feedback = rootView.findViewById(R.id.feedback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("feedback");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String text2 = feedback.getText().toString();
                        String username2 = username;
                        final feedback1 Feedback = new feedback1(text2, username2);
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!text2.equals(null) || !text2.equals("")) {
                                    mDatabase.push().setValue(Feedback);
                                    Toast.makeText(getActivity(),"feedback sudah terkirim", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getActivity(), " form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
