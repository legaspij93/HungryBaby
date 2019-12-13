package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hungrybaby.Model.Cart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView listViewCarts;
    List<Cart> carts;

    ImageView trashBtn;
    DatabaseReference databaseCarts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        databaseCarts = FirebaseDatabase.getInstance().getReference("cart");
        listViewCarts = findViewById(R.id.listViewCart);

        trashBtn = findViewById(R.id.trashBtn);

        carts = new ArrayList<>();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu:
                        Intent menuIntent = new Intent(CartActivity.this, menuActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.nav_cart:
                        Intent cartIntent = new Intent(CartActivity.this, CartActivity.class);
                        startActivity(cartIntent);
                        break;
                    case R.id.nav_orders:
                        Intent ordersIntent = new Intent(CartActivity.this, OrderActivity.class);
                        startActivity(ordersIntent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseCarts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carts.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting blog
                    Cart cart = postSnapshot.getValue(Cart.class);
                    //adding blog to the list
                    carts.add(cart);
                }

                //creating adapter
                CartList blogAdapter = new CartList(CartActivity.this, carts);
                //attaching adapter to the listview
                listViewCarts.setAdapter(blogAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void checkoutOrder(View v){
//        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
//        startActivity(intent);
    }
}
