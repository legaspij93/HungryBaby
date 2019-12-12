package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {

    TextView userName, emailAddress, contact, address;
    FirebaseAuth mAuth;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        userName = findViewById(R.id.userName);
        emailAddress = findViewById(R.id.emailAddress);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.address);

        userName.setText(mAuth.getCurrentUser().getDisplayName());
        emailAddress.setText(mAuth.getCurrentUser().getEmail());

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                        contact.setText(dataSnapshot1.child("contactNum").getValue(String.class));
                        address.setText(dataSnapshot1.child("address").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
