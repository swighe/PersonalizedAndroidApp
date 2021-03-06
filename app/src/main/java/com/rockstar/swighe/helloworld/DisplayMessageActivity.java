package com.rockstar.swighe.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}

/*

TO NYC
------
curl "http://mybusnow.njtransit.com/bustime/map/getRoutesForAgency.jsp?agency=21922"
<?xml version="1.0"?>
<error>No routes found</error>

TO FORT LEE
-----------
curl "http://mybusnow.njtransit.com/bustime/map/getRoutesForAgency.jsp?agency=26229"
<?xml version="1.0"?>
<error>No routes found</error>
 */