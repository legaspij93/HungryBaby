package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu:
                        Intent menuIntent = new Intent(OrderActivity.this, CategoryActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.nav_cart:
                        Intent cartIntent = new Intent(OrderActivity.this, CartActivity.class);
                        startActivity(cartIntent);
                        break;
                    case R.id.nav_orders:
                        Intent ordersIntent = new Intent(OrderActivity.this, OrderActivity.class);
                        startActivity(ordersIntent);
                        break;
                    case R.id.nav_profile:
                        Intent profileIntent = new Intent(OrderActivity.this, profileActivity.class);
                        startActivity(profileIntent);
                        break;
                }
                return false;
            }
        });
    }
}
