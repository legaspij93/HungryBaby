package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.CurrentOrder;
import com.example.hungrybaby.Model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    DatabaseReference databaseCurrent;

    TextView deliveryAddress, subtotal, total, status;
    List<CurrentOrder> orders;
    List<Cart> carts;

    ListView listViewOrder;
//    Boolean orderExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

//        orderExists = true;
        databaseCurrent = FirebaseDatabase.getInstance().getReference("currentOrder");

        listViewOrder = findViewById(R.id.listViewOrder);

        orders = new ArrayList<>();

        deliveryAddress = findViewById(R.id.deliveryAddress);
        subtotal = findViewById(R.id.subtotal);
        total = findViewById(R.id.total);
        status = findViewById(R.id.status);

//        databaseCurrent.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.hasChild("currentOrder")){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        if(orderExists == true){
//            setContentView(R.layout.activity_order);
//
//        }else{
//            setContentView(R.layout.activity_no_order);
//        }

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

    @Override
    protected void onStart() {
        super.onStart();

        databaseCurrent.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting blog
                    CurrentOrder order = postSnapshot.getValue(CurrentOrder.class);
                    //adding blog to the list
                    orders.add(order);
                }
//                int sum =0;
//
//                for(int i=0;i<carts.size();i++){
//                    int price = Integer.parseInt(carts.get(i).getCost());
//                    sum = sum + (price*carts.get(i).getQuantity());
//                }
//                String sub = String.valueOf(sum);
//                subTotal.setText(sub);
//                String total = String.valueOf(sum + 50);
//                totalPrice.setText(total);
                carts = orders.get(0).getOrders();
                //creating adapter
                CartList blogAdapter = new CartList(OrderActivity.this, carts);
                //attaching adapter to the listview
                listViewOrder.setAdapter(blogAdapter);

                deliveryAddress.setText(orders.get(0).getDeliverAddress());
                total.setText(orders.get(0).getTotalCost());
                status.setText(orders.get(0).getStatus());

                String tc = orders.get(0).getTotalCost();
                int sub = Integer.parseInt(tc) - 50;
                subtotal.setText(Integer.toString(sub));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
