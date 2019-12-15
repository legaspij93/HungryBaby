package com.example.hungrybaby;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hungrybaby.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastOrdersActivity extends AppCompatActivity {

    DatabaseReference databaseOrders;
    FirebaseAuth mAuth;

    ListView listViewOrder;

    List<Order> orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Past Orders");

        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");
        mAuth = FirebaseAuth.getInstance();

        listViewOrder = findViewById(R.id.listViewOrder);

        orders = new ArrayList<>();

    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orders.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting blog
                    Order order = postSnapshot.getValue(Order.class);
                    //adding blog to the list
                    orders.add(order);
                }

                //creating adapter
                OrderList orderAdapter = new OrderList(PastOrdersActivity.this, orders);
                //attaching adapter to the listview
                listViewOrder.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
