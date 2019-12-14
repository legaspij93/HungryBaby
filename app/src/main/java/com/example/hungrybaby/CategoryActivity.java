package com.example.hungrybaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu:
                        Intent menuIntent = new Intent(CategoryActivity.this, CategoryActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.nav_cart:
                        Intent cartIntent = new Intent(CategoryActivity.this, CartActivity.class);
                        startActivity(cartIntent);
                        break;
                    case R.id.nav_orders:
                        Intent ordersIntent = new Intent(CategoryActivity.this, OrderActivity.class);
                        startActivity(ordersIntent);
                        break;
                    case R.id.nav_profile:
                        Intent profileIntent = new Intent(CategoryActivity.this, profileActivity.class);
                        startActivity(profileIntent);
                        break;
                }
                return false;
            }
        });
    }

    public void catFries(View v){
        Intent intent = new Intent(CategoryActivity.this, menuActivity.class);
        intent.putExtra("CAT", "fries");
        startActivity(intent);
    }

    public void catBurgers(View v){
        Intent intent = new Intent(CategoryActivity.this, menuActivity.class);
        intent.putExtra("CAT", "burgers");
        startActivity(intent);
    }

    public void catWings(View v){
        Intent intent = new Intent(CategoryActivity.this, menuActivity.class);
        intent.putExtra("CAT", "wings");
        startActivity(intent);
    }

    public void catDrinks(View v){
        Intent intent = new Intent(CategoryActivity.this, menuActivity.class);
        intent.putExtra("CAT", "drinks");
        startActivity(intent);
    }

}
