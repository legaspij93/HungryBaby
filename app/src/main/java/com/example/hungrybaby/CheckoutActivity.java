package com.example.hungrybaby;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.CurrentOrder;
import com.example.hungrybaby.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    DatabaseReference databaseUsers, databaseOrders, databaseCurrent;
    FirebaseAuth mAuth;
    Intent cartIntent;

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

        cartIntent = getIntent();

        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseOrders = FirebaseDatabase.getInstance().getReference("orders");
        databaseCurrent = FirebaseDatabase.getInstance().getReference("currentOrder");

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

    public void placeOrder(View v){

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

        Bundle cartBundle = cartIntent.getBundleExtra("BUNDLE");
        final List<Cart> carts = (List<Cart>) cartBundle.getSerializable("CARTLIST");

        databaseCurrent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = databaseCurrent.push().getKey();


                CurrentOrder order = new CurrentOrder();

                Date date = new Date(System.currentTimeMillis());
                String timestamp = formatter.format(date);

                order.setOrderId(id);
                order.setUser(mAuth.getCurrentUser().getEmail());
                order.setOrders(carts);
                order.setDeliverAddress(addressInput.getText().toString());
                order.setTotalCost(cartIntent.getStringExtra("TOTAL"));
                order.setDateOrdered(timestamp);
                databaseCurrent.child(id).setValue(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseOrders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = databaseOrders.push().getKey();


                Order order = new Order();

                Date date = new Date(System.currentTimeMillis());
                String timestamp = formatter.format(date);

                order.setOrderId(id);
                order.setUser(mAuth.getCurrentUser().getEmail());
                order.setOrders(carts);
                order.setDeliverAddress(addressInput.getText().toString());
                order.setTotalCost(cartIntent.getStringExtra("TOTAL"));
                order.setDateOrdered(timestamp);
                databaseOrders.child(id).setValue(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Toast.makeText(this, "Order Success!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CheckoutActivity.this, OrderActivity.class);
        startActivity(intent);
    }


}
