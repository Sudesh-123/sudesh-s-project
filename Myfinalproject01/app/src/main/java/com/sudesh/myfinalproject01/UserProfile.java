package com.sudesh.myfinalproject01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;

    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Reference to Firebase Database
        reference = FirebaseDatabase.getInstance().getReference("users");

        //pass register data  to the user prfile
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        password = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //showalldata
        showAllUserData();
    }

//// show user profile data  an field
    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phoneNo.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);
    }
  /// will update data
    public void update(View view){
        if(isNameChanged() || isPasswordChanged() ){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Data is same and cannot be updated",Toast.LENGTH_LONG).show();
        }
    }


    // Delete button click event
    public void delete(View view) {
        reference.child(_USERNAME).removeValue();
        Toast.makeText(this, "User deleted", Toast.LENGTH_LONG).show();
        finish();
    }
//// will show message password changed or not
    private boolean isPasswordChanged() {
        String newPassword = password.getEditText().getText().toString().trim();
        if (!_PASSWORD.equals(newPassword)) {
            reference.child(_USERNAME).child("password").setValue(newPassword);
            _PASSWORD = newPassword;
            return true;
        } else {
            return false;
        }
    }
  /////will show user name changed or not
    private boolean isNameChanged() {
        String newName = fullName.getEditText().getText().toString().trim();
        if (!_NAME.equals(newName)) {
            reference.child(_USERNAME).child("name").setValue(newName);
            _NAME = newName;
            return true;
        } else {
            return false;
        }

    }
}
