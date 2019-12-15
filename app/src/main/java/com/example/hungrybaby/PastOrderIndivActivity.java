package com.example.hungrybaby;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PastOrderIndivActivity extends AppCompatActivity {

    TextView deliveryAddress, subtotal, total;

    ListView listViewOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Past Order");

        listViewOrder = findViewById(R.id.listViewOrder);
        deliveryAddress = findViewById(R.id.deliveryAddress);
        subtotal = findViewById(R.id.subtotal);
        total = findViewById(R.id.total);

        Intent intent = getIntent();

        deliveryAddress.setText(intent.getStringExtra("ORDER_ADDRESS"));
        total.setText(intent.getStringExtra("ORDER_TOTAL"));

        String tc = intent.getStringExtra("ORDER_TOTAL");
        int sub = Integer.parseInt(tc) - 50;
        subtotal.setText(Integer.toString(sub));
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        Bundle cartBundle = intent.getBundleExtra("ORDER_LIST");
        List<Cart> carts = (List<Cart>) cartBundle.getSerializable("CARTLIST");        //creating adapter
        CartList blogAdapter = new CartList(PastOrderIndivActivity.this, carts);
        //attaching adapter to the listview
        listViewOrder.setAdapter(blogAdapter);
    }
}
