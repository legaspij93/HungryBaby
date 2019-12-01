package com.example.hungrybaby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hungrybaby.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {

    EditText nameInput, emailInput, numberInput, addressInput, passwordInput;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        numberInput = findViewById(R.id.numberInput);
        addressInput = findViewById(R.id.addressInput);
        passwordInput = findViewById(R.id.passwordInput);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    }

    public void registerUser(View v){
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String number = numberInput.getText().toString();
        String address = addressInput.getText().toString();
        String password = passwordInput.getText().toString();

        String id = databaseUsers.push().getKey();

        User user = new User(id, name, email, number, password, address);

        databaseUsers.child(id).setValue(user);

        Toast.makeText(this, "Registered User", Toast.LENGTH_LONG).show();

        finish();
    }

    public void renderLogin(View v){
        finish();
    }
}
