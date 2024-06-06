package com.sudesh.myfinalproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashBoard extends AppCompatActivity {
    private String name, username, email, phoneNo,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        // Retrieve user data from intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        phoneNo = intent.getStringExtra("phoneNo");
        password = intent.getStringExtra("password");
    }


    public void openUserProfile(View view) {
        Intent intent = new Intent(DashBoard.this, UserProfile.class);
        intent.putExtra("name", name);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("phoneNo", phoneNo);
        intent.putExtra("password", password);
        startActivity(intent);
    }


    public void pt(View view){

        startActivity(new Intent(this, Patient.class));
    }

    public void ot(View view){

        startActivity(new Intent(this, Otherp.class));
    }
    public void map(View view){

        startActivity(new Intent(this, MapsActivity.class));
    }
    public void shock(View view){
        startActivity(new Intent(this, Accelerometer.class));
    }



    public void profile1(View view){
        startActivity(new Intent(this, UserProfile.class));
    }


    public void ins(View view){
        startActivity(new Intent(this, Instractions.class));
    }

}
