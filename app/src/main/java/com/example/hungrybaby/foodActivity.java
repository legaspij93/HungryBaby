package com.example.hungrybaby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class foodActivity extends AppCompatActivity {

    TextView singleItemName, singleItemPrice, price;

    DatabaseReference databaseItem, databaseCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        singleItemName = findViewById(R.id.singleItemName);
        singleItemPrice = findViewById(R.id.singleItemPrice);
        price = findViewById(R.id.price);

        databaseCart = FirebaseDatabase.getInstance().getReference("cart");

        Intent intent = getIntent();

        singleItemName.setText(intent.getStringExtra("NAME"));
        singleItemPrice.setText(intent.getStringExtra("PRICE"));
        price.setText(intent.getStringExtra("PRICE"));
    }

    public void addItem(View v){
        String name = singleItemName.getText().toString();
        String price = singleItemPrice.getText().toString();

        String id = databaseCart.push().getKey();

        Item item = new Item(id, name, price, price);
        Cart cart = new Cart(name, price);

        databaseCart.child(id).setValue(cart);

        Toast.makeText(this, "Added to Cart", Toast.LENGTH_LONG).show();

        finish();

    }
}
