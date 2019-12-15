package com.example.hungrybaby;

import android.app.PendingIntent;
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
import com.google.firebase.auth.FirebaseUser;
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
    Intent cartIntent;

    EditText addressInput, contactNumber;

    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 30000;

    private boolean mTimerRunning;
    private TextView mTextViewCountDown, stat;
    private long mTimeLeftInMillis;
    private long mEndTime;

    String idQuery;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String userID = firebaseUser.getUid();



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

        resetTimer();
        startTimer();

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

        Bundle cartBundle = cartIntent.getBundleExtra("BUNDLE");
        final List<Cart> carts = (List<Cart>) cartBundle.getSerializable("CARTLIST");

        databaseCurrent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = databaseCurrent.push().getKey();
                idQuery = id;


                CurrentOrder order = new CurrentOrder();

                Date date = new Date(System.currentTimeMillis());
                String timestamp = formatter.format(date);

                order.setOrderId(id);
                order.setUser(mAuth.getCurrentUser().getEmail());
                order.setOrders(carts);
                order.setDeliverAddress(addressInput.getText().toString());
                order.setTotalCost(cartIntent.getStringExtra("TOTAL"));
                order.setDateOrdered(timestamp);
                order.setStatus("Preparing Order!");
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
                Toast.makeText(CheckoutActivity.this, "Timer Finish", Toast.LENGTH_SHORT).show();


            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        final String CHANNEL_ID = "notif";
        final int NOTIFICATION_ID = 001;

        int sec = (int) (mTimeLeftInMillis / 1000) % 60;
        if( sec == 20)
        {

            databaseCurrent.child(idQuery).child("status").setValue("Order is on the way...");
            Toast.makeText(CheckoutActivity.this, "WEW", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle("Food Delivery Status");
            builder.setContentText("Your food is on its way....");
            builder.setTicker("New Message Alert!");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent intent = new Intent(CheckoutActivity.this, profileActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(CheckoutActivity.this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        }

        if( sec == 10)
        {

            databaseCurrent.child(idQuery).child("status").setValue("Driver is nearby...");
            Toast.makeText(CheckoutActivity.this, "WEW", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle("Food Delivery Status");
            builder.setContentText("Driver is nearby....");
            builder.setTicker("New Message Alert!");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent intent = new Intent(CheckoutActivity.this, profileActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(CheckoutActivity.this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        }

         if( sec == 5)
        {

            databaseCurrent.child(idQuery).child("status").setValue("Your food has arrived...");
            Toast.makeText(CheckoutActivity.this, "WEW", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle("Food Delivery Status");
            builder.setContentText("Your food has arrived");
            builder.setTicker("New Message Alert!");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            Intent intent = new Intent(CheckoutActivity.this, profileActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(CheckoutActivity.this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
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
