package com.rockstar.swighe.helloworld;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationManager extends AppCompatActivity {

    private FusedLocationProviderClient client;
    private Location location;

    public Location getLocation() {

        //requestPermission();
        if (ActivityCompat.checkSelfPermission(LocationManager.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return null;
        }

        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(LocationManager.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {

                if(loc!= null){
                    location = loc;
                }
            }
        });

        return location;
    }


    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
