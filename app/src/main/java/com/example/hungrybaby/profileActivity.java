package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu:
                        Intent menuIntent = new Intent(profileActivity.this, menuActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.nav_cart:
                        Intent cartIntent = new Intent(profileActivity.this, CartActivity.class);
                        startActivity(cartIntent);
                        break;
                    case R.id.nav_orders:
                        Intent ordersIntent = new Intent(profileActivity.this, OrderActivity.class);
                        startActivity(ordersIntent);
                        break;
                    case R.id.nav_profile:
                        Intent profileIntent = new Intent(profileActivity.this, profileActivity.class);
                        startActivity(profileIntent);
                        break;
                }
                return false;
            }
        });

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
