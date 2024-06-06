package com.sudesh.myfinalproject01;

public class Accident {
    private String accidentId;
    private double latitude;
    private double longitude;

    public Accident() {
        // Required empty constructor for Firebase
    }

    public Accident(String accidentId, double latitude, double longitude) {
        this.accidentId = accidentId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAccidentId() {
        return accidentId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
