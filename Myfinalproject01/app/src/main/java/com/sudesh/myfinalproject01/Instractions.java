package com.sudesh.myfinalproject01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Instractions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instractions);
    }

    public void health(View view){

        startActivity(new Intent(this, Health.class));
    }

    public void speed(View view){

        startActivity(new Intent(this, Speed.class));
    }

    public void vehical(View view){

        startActivity(new Intent(this, Vehical.class));
    }

    public void direct(View view){

        startActivity(new Intent(this, direction.class));
    }


}