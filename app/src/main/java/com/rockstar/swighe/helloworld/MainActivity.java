package com.rockstar.swighe.helloworld;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.njt_status_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public MainActivity() {
    }

    /** Called when the user taps the Send button */
    public void sendUNUSED(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        requestPermission(Manifest.permission.READ_SMS);
        sendSMS("9493501251", message);
    }

    private void requestPermission(String perm){
        ActivityCompat.requestPermissions(this, new String[]{perm}, 1);
    }

    private void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    /** Called when the user taps the SendLocation button */
    public void sendLocation(View view) {
        LocationManager locationManager = new LocationManager();
        Location location = locationManager.getLocation();
        TextView locationText = findViewById(R.id.locationText);
        locationText.setText(location.toString());

        //requestPermission(Manifest.permission.READ_SMS);
        //sendSMS("9493501251", message);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position > 0){
            switch (position) {
                case 1:
                    //Uri uri = Uri.parse("http://mybusnow.njtransit.com/bustime/eta/eta.jsp?route=---&direction=---&stop=---&id=21922&showAllBusses=on&findstop=on");
                    Uri uri = Uri.parse("http://mybusnow.njtransit.com/bustime/eta/eta.jsp?route=158&direction=New%20York&stop=PORT%20IMPERIAL%20BLVD.%20%2B%20NORTH%20PARK%20CT.&id=21922&showAllBusses=on");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                case 2:
                    //uri = Uri.parse("http://mybusnow.njtransit.com/bustime/eta/eta.jsp?route=---&direction=---&stop=---&id=26229&showAllBusses=on&findstop=on");
                    uri = Uri.parse("http://mybusnow.njtransit.com/bustime/eta/eta.jsp?route=158&direction=Fort%20Lee&stop=PORT%20AUTHORITY%20BUS%20TERMINAL&id=26229&showAllBusses=on");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
            }
        } else {
            // show toast select gender
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
