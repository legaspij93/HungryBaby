package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungrybaby.Model.Cart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    ListView listViewCarts;
    List<Cart> carts;

    TextView totalPrice;
    TextView subTotal;

    ImageView trashBtn;
    DatabaseReference databaseCarts, databaseUsers;

    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 15000;

    private boolean mTimerRunning;
    private TextView mTextViewCountDown, stat;
    private long mTimeLeftInMillis;
    private long mEndTime;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String userID = firebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        databaseCarts = FirebaseDatabase.getInstance().getReference("cart");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
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
                        Intent menuIntent = new Intent(CartActivity.this, CategoryActivity.class);
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
//        Intent intent = new Intent(CartActivity.this, profileActivity.class);
//        startActivity(intent);
//        resetTimer();
//        startTimer();
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
//        updateCountDownText();
    }

    private void startTimer() {
        mEndTime = START_TIME_IN_MILLIS + mTimeLeftInMillis;
//        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(CartActivity.this, "Timer Finish", Toast.LENGTH_SHORT).show();


            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        final String CHANNEL_ID = "notif";
        final int NOTIFICATION_ID = 001;

        int sec = (int) (mTimeLeftInMillis / 1000) % 60;
        if( sec == 7)
        {

            databaseUsers.child(userID).child("address").setValue("Quezon City");
            Toast.makeText(CartActivity.this, "WEW", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle("Food Delivery Status");
            builder.setContentText("Your food is on its way....");
            builder.setTicker("New Message Alert!");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent intent = new Intent(CartActivity.this, profileActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(CartActivity.this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


        }

        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

//        mTextViewCountDown.setText(timeLeftFormatted);
    }

}
