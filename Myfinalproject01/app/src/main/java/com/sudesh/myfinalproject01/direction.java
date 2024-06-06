package com.sudesh.myfinalproject01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class direction extends AppCompatActivity {

    private Button navigation_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        navigation_btn = findViewById(R.id.map_dir);

        //google map-direction
        navigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 6.872400502524298;
                double longitude = 79.92513686707812;
                String uri = "http://maps.google.com/maps?saddr=&daddr=" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Google Maps app is not installed on your device.",
                            Toast.LENGTH_LONG).show();
                }
            }});
    }
}