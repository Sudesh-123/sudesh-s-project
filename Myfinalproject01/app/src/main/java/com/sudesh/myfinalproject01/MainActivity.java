package com.sudesh.myfinalproject01;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Register(View view){

        startActivity(new Intent(this, Registerlayer.class));
    }



    public void septr(View view){
        startActivity(new Intent(this, loginlayer.class));
    }



}

