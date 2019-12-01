package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hungrybaby.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    }

    public void loginUser(View v){
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("email").getValue(String.class).equals(email)){
                        if(dataSnapshot1.child("password").getValue(String.class).equals(password)) {
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void renderRegister(View v){
        Intent intent = new Intent(loginActivity.this, registerActivity.class);
        startActivity(intent);
    }
}
