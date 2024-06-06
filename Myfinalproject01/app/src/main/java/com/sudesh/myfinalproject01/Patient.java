package com.sudesh.myfinalproject01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Patient extends AppCompatActivity {

    private Button button;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private DatabaseReference databaseReference;
    private FirebaseAnalytics firebaseAnalytics;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        button = findViewById(R.id.openCamera);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        // Get a reference to the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open camera to capture photo
                Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE);

                // Check if the location permission is granted
                if (ContextCompat.checkSelfPermission(Patient.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request the location permission if it is not granted
                    ActivityCompat.requestPermissions(Patient.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                } else {
                    // If the location permission is granted, call the getCurrentLocation() method
                    getCurrentLocation();
                }
            }
        });
    }

    // Create a LocationCallback to handle location updates
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            // Loop through the locations received in the locationResult
            for (Location location : locationResult.getLocations()) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Store latitude and longitude in Firebase Database
                databaseReference.child("location").child("latitude").setValue(latitude);
                databaseReference.child("location").child("longitude").setValue(longitude);

                // Do something with the latitude and longitude values (display in a toast message in this case)
                Toast.makeText(Patient.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Method to request the current location
    private void getCurrentLocation() {
        // Create a LocationRequest object
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);

        // Request location updates using the FusedLocationProviderClient and the LocationRequest object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If the location permission is granted, call the getCurrentLocation() method
                getCurrentLocation();
            } else {
                // If the location permission is denied, display a toast message
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the captured image
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            // Upload the image to Firebase Storage
            uploadImageToFirebase(imageBitmap);
        }
    }

    // Upload image to Firebase Storage
    private void uploadImageToFirebase(Bitmap imageBitmap) {
        // Create a ByteArrayOutputStream to compress the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // Define the path and filename for the image in Firebase Storage
        String fileName = "patient_image.jpg";
        StorageReference imageRef = storageReference.child(fileName);

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, get the download URL
                imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // Get the download URL
                            Uri downloadUri = task.getResult();

                            // Store the download URL in the Firebase Realtime Database
                            databaseReference.child("images").child("patient_image").setValue(downloadUri.toString());

                            // Display a success message
                            Toast.makeText(Patient.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Error getting the download URL
                            Toast.makeText(Patient.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Image upload failed, display an error message
                Toast.makeText(Patient.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}




