package com.example.hungrybaby;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hungrybaby.Model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;
    FirebaseAuth mAuth;

    EditText addressInput, contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        addressInput = findViewById(R.id.addressInput);
        contactNumber = findViewById(R.id.contactNumber);

        //this sets the text/title in the Action Bar//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                        contactNumber.setText(dataSnapshot1.child("contactNum").getValue(String.class));
                        addressInput.setText(dataSnapshot1.child("address").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



    }


}
