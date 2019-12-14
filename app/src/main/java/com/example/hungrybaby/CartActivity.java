package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    TextView totalPrice;
    TextView subTotal;

    ImageView trashBtn;
    DatabaseReference databaseCarts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        databaseCarts = FirebaseDatabase.getInstance().getReference("cart");
        listViewCarts = findViewById(R.id.listViewCart);
        subTotal = findViewById(R.id.subtotal);
        totalPrice = findViewById(R.id.total);
        trashBtn = findViewById(R.id.trashBtn);

        carts = new ArrayList<>();

        //this sets the text/title in the Action Bar//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //this makes the icons get colored when pressed//
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
                    case R.id.nav_profile:
                        Intent profileIntent = new Intent(CartActivity.this, profileActivity.class);
                        startActivity(profileIntent);
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
                int sum =0;

                for(int i=0;i<carts.size();i++){
                    int price = Integer.parseInt(carts.get(i).getCost());
                    sum = sum + (price*carts.get(i).getQuantity());
                }
                String sub = String.valueOf(sum);
                subTotal.setText(sub);
                String total = String.valueOf(sum + 50);
                totalPrice.setText(total);

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
        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
        startActivity(intent);
    }
}
