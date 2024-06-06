package com.sudesh.myfinalproject01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginlayer extends AppCompatActivity {

    TextView textView;
    Button callSignUp, login_btn;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginlayer);


        //Hooks
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        callSignUp = findViewById(R.id.callSignUp);
    }


    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
  ///////
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String passwordFromDB = snapshot.child("password").getValue(String.class);
                        if (passwordFromDB.equals(userEnteredPassword)) {
                            username.setError(null);
                            username.setErrorEnabled(false);
                            String nameFromDB = snapshot.child("name").getValue(String.class);
                            String usernameFromDB = snapshot.child("username").getValue(String.class);
                            String phoneNoFromDB = snapshot.child("phoneNo").getValue(String.class);
                            String emailFromDB = snapshot.child("email").getValue(String.class);

                            // Create intent to Dashboard activity
                            Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                            startActivity(intent);

                            // Create intent to UserProfile activity and pass user data
                            Intent intentUserProfile = new Intent(getApplicationContext(), DashBoard.class);
                            intentUserProfile.putExtra("name", nameFromDB);
                            intentUserProfile.putExtra("username", usernameFromDB);
                            intentUserProfile.putExtra("email", emailFromDB);
                            intentUserProfile.putExtra("phoneNo", phoneNoFromDB);
                            intentUserProfile.putExtra("password", passwordFromDB);
                            startActivity(intentUserProfile);

                            // Finish the login activity to prevent user from going back
                            finish();
                        } else {
                            password.setError("Wrong Password");
                            password.requestFocus();
                        }
                    }
                } else {
                    username.setError("No such User exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}

