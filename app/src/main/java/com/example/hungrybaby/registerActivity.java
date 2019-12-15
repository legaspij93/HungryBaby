package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungrybaby.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {

    EditText nameInput, emailInput, numberInput, addressInput, passwordInput;
    TextView text;
    Typeface font;

    DatabaseReference databaseUsers;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //this is in charge of applying the font
        text = (TextView) findViewById(R.id.label);
        font = Typeface.createFromAsset(this.getAssets(),"fonts/Baloo-Regular.ttf");
        text.setTypeface(font);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        numberInput = findViewById(R.id.numberInput);
        addressInput = findViewById(R.id.addressInput);
        passwordInput = findViewById(R.id.passwordInput);


        mAuth = FirebaseAuth.getInstance();

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    }

    public void registerUser(View v){
        final String name = nameInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String number = numberInput.getText().toString();
        final String address = addressInput.getText().toString();
        final String password = passwordInput.getText().toString();

//        String id = databaseUsers.push().getKey();
//
//        final User user = new User(id, name, email, number, password, address);
//
//        databaseUsers.child(id).setValue(user);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    User newUser = new User(
                            name,
                            email,
                            number,
                            address
                    );

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser uxer = mAuth.getCurrentUser();
                            UserProfileChangeRequest profile= new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            uxer.updateProfile(profile);
                            Toast.makeText(getApplicationContext(), "Registered User", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });



        finish();
    }

    public void renderLogin(View v){
        finish();
    }
}
