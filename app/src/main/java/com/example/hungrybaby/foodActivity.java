package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class foodActivity extends AppCompatActivity {

    String category;

    TextView singleItemName, singleItemPrice, price;
    ImageView singleItemImage;
    ElegantNumberButton qtyBtn;

    DatabaseReference databaseCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        category = intent.getStringExtra("CAT");

        if(category.equals("drinks")){
            setContentView(R.layout.activity_food_three);
        }
        else {
            setContentView(R.layout.activity_food_two);
        }

        singleItemName = findViewById(R.id.singleItemName);
        singleItemPrice = findViewById(R.id.singleItemPrice);
        singleItemImage = findViewById(R.id.singleItemImage);
        price = findViewById(R.id.price);
        qtyBtn = findViewById(R.id.qtyBtn);

        databaseCart = FirebaseDatabase.getInstance().getReference("cart");



        singleItemName.setText(intent.getStringExtra("NAME"));
        singleItemPrice.setText(intent.getStringExtra("PRICE"));
        price.setText(intent.getStringExtra("PRICE"));
        String imageLink = intent.getStringExtra("IMAGE");
        Picasso.get().load(imageLink).into(singleItemImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addItem(View v){
        final String name = singleItemName.getText().toString();
        final String price = singleItemPrice.getText().toString();
        final Boolean found;

        databaseCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int found = 0;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("order").getValue(String.class).equals(name)){
                        int quantity = dataSnapshot1.child("quantity").getValue(Integer.class);
                        quantity+= Integer.parseInt(qtyBtn.getNumber());
                        found++;
                        dataSnapshot1.getRef().child("quantity").setValue(quantity);
                    }
                }
                if(found==0){
                    String id = databaseCart.push().getKey();


                    Cart cart = new Cart();

                    cart.setOrder(name);
                    cart.setCost(price);
                    cart.setQuantity(Integer.parseInt(qtyBtn.getNumber()));
                    databaseCart.child(id).setValue(cart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Added to Cart", Toast.LENGTH_LONG).show();

        finish();

    }
}
