package com.example.hungrybaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungrybaby.Model.Item;
import com.example.hungrybaby.ViewHolder.MyAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.hungrybaby.R.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Item> options;
    MyAdapter adapter;
    ArrayList<Item> itemArrayList;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

//        BottomNavigationView bottomNav = findViewById(id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);

        BottomNavigationView bottomNav = findViewById(id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case id.nav_menu:
                        Intent menuIntent = new Intent(MainActivity.this, menuActivity.class);
                        startActivity(menuIntent);
                        break;
                    case id.nav_cart:
                        Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                        startActivity(cartIntent);
                        break;
                    case id.nav_orders:
                        Intent ordersIntent = new Intent(MainActivity.this, OrderActivity.class);
                        startActivity(ordersIntent);
                        break;
                }
                return false;
            }
        });


        mAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new MenuFragment()).commit();

        Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getDisplayName() , Toast.LENGTH_LONG).show();
    }


//    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            Fragment selectedFragment = null;
//
//            switch (menuItem.getItemId()){
//                case id.nav_menu:
//                    selectedFragment = new MenuFragment();
//                    break;
//
//                case id.nav_cart:
//                    selectedFragment = new CartFragment();
//                    break;
//
//                case id.nav_orders:
//                    selectedFragment = new OrdersFragment();
//                    break;
//
//                case id.nav_profile:
//                    selectedFragment = new ProfileFragment();
//                    break;
//
//            }
//
//            getSupportFragmentManager().beginTransaction().replace(id.fragment_container,selectedFragment).commit();
//            return true;
//        }
//    };
}
