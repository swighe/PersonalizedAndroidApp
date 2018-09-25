package com.rockstar.swighe.helloworld;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lat;
    private double lng;
    private static Map map = null;
    private DatabaseReference mDatabase;
    private ValueEventListener mPlacesListener;
    private static boolean isDataReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("On Create called");
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        lat = Double.parseDouble(intent.getStringExtra(LocationActivity.LATKEY));
        lng = Double.parseDouble(intent.getStringExtra(LocationActivity.LONGKEY));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createDefaultLocationMap();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        plotMap();
    }

    private void plotMap() {
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker to current location
        //LatLng currLocation = new LatLng(lat, lng);
        //addLocationMarker(mMap, "myCurrentLocation", currLocation);
        //mMap.addCircle(new CircleOptions().center(currLocation).radius(200));
        for(Object location: map.entrySet()) {
            addLocationMarker(googleMap, (String)((Map.Entry)location).getKey(), (LatLng) ((Map.Entry) location).getValue());
        }
    }

    private void addLocationMarker(GoogleMap googleMap, String markerTitle, LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(markerTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private Map createDefaultLocationMap() {
        if (map == null) {
            map = new HashMap();
            if (mDatabase.child("places") == null) {
                map.put("AppNexus", new LatLng(40.7420861, -73.99133));
            } else {
                mPlacesListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // New data at this path. This method will be called after every change in the
                        // data at this path or a subpath.

                        // Get the data as Message objects
                        //print(TAG, "Number of messages: " + dataSnapshot.getChildrenCount());
                        for (DataSnapshot child : dataSnapshot.child("places").getChildren()) {
                            // Extract a Message object from the DataSnapshot

                            Place place = child.getValue(Place.class);
                            map.put(place.getName(), new LatLng(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude())));
                            //Log.d(TAG, "message text:" + message.getText());
                            //Log.d(TAG, "message sender name:" + message.getName());
                            // [END_EXCLUDE]
                        }
                        isDataReady = true;
                        System.out.println("Setting isDataReady : " + isDataReady);
                        plotMap();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Could not successfully listen for data, log the error
                        //Log.e(TAG, "messages:onCancelled:" + error.getMessage());
                    }
                };
                mDatabase.addValueEventListener(mPlacesListener);
            }
        }
        return map;
    }
}
