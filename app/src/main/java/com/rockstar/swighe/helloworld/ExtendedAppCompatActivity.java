package com.rockstar.swighe.helloworld;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

/*
    Extend the base class AppCompatActivity with utilities for sending texts, requesting permissions etc.
 */
public class ExtendedAppCompatActivity extends AppCompatActivity {


    public void sendSMS(String phoneNo, String msg) {
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


    public void requestPermission(String perm){
        ActivityCompat.requestPermissions(this, new String[]{perm}, 1);
    }

}
