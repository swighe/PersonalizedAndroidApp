package com.rockstar.swighe.helloworld;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.fitness.data.MapValue;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends ExtendedAppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView textLatitude;
    private TextView textLongitude;
    private MapView mapView;
    public static final String LATKEY = "lat";
    public static final String LONGKEY = "long";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textLatitude = findViewById(R.id.latitudeText);
        textLongitude = findViewById(R.id.longitudeText);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_GSERVICES) != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            return;
//        }
//        mapView.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);
    }


    @Override
    public void onLocationChanged(Location location) {
        textLatitude.setText("" + location.getLatitude());
        textLongitude.setText("" + location.getLongitude());
        textLongitude.setText("" + location.getLongitude());
        showMap(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /** Called when the user taps the Send button */
    public void sendLocation(View view) {
        String message = textLatitude.getText() + "," + textLongitude.getText();
        requestPermission(Manifest.permission.READ_SMS);
        sendSMS(getString(R.string.chie_phone), message);
    }

    public void displayLocaton(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(LATKEY, textLatitude.getText());
        intent.putExtra(LONGKEY, textLongitude.getText());
        startActivity(intent);

    }

    private void showMap(final double lat, final double lng) {
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(lat, lng);
                googleMap.addMarker(new MarkerOptions()
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_flag))
                        .anchor(0.0f, 1.0f)
                        .position(latLng));
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                googleMap.moveCamera(cameraUpdate);
            }
        });
    }
}
