package com.sudesh.myfinalproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
    ImageView image1;

    Animation sideAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        image1 = findViewById(R.id.image1);
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        image1.setAnimation(sideAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(splash.this, MainActivity.class);
                startActivity(intent);

            }
        },5000);
    }
}