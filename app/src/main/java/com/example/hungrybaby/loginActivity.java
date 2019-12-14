package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hungrybaby.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;

    DatabaseReference databaseUsers, databaseCart;

    FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//
//        if(firebaseUser!=null){
//            startActivity(new Intent(loginActivity.this, menuActivity.class));
//        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser!=null){
            startActivity(new Intent(loginActivity.this, CategoryActivity.class));
        }

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseCart = FirebaseDatabase.getInstance().getReference("cart");
    }

    public void loginUser(View v){
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                databaseCart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(task.isSuccessful()) {
                    Intent intent = new Intent(loginActivity.this, CategoryActivity.class);
                    Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });

//        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    if(dataSnapshot1.child("email").getValue(String.class).equals(email)){
//                        if(dataSnapshot1.child("password").getValue(String.class).equals(password)) {
//                            Intent intent = new Intent(loginActivity.this, menuActivity.class);
//                            Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                            startActivity(intent);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    public void renderRegister(View v){
        Intent intent = new Intent(loginActivity.this, registerActivity.class);
        startActivity(intent);
    }
}
