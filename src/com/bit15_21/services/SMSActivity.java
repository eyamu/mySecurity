package com.bit15_21.services;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bit15_21.helpers.Constants;
import com.bit15_21.helpers.JSONParser;
import com.bit15_21.helpers.SessionManager;
import com.bit15_21.mysecurity.ReportInsecurity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SMSActivity extends Activity {
    JSONParser jsonParser = new JSONParser();//JSon Object
    
    SessionManager session;
     GPSTrackerActivity gps;     

    String[] contacts = {"0784197545", "075219745", "0789605920", "0706851883"};//just for testing but will be dynamically picked from the database




    // JSON Node names
    private static final String TAG_SUCCESS = "success"; 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.main);

        Button launchApp = new Button(SMSActivity.this);


        // button click event
        launchApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //  Sending a message
                new ReportInsecurity(getApplicationContext()).execute();
            }
        });
    }
    //Send the message to your emergency contacts
    public void sendSMSMessage(String phone, String message) {
        Log.i("Send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent sucessfully.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    //Picking today's date
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("DD-MM-yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    
    
    public void report(){
    	gps=new GPSTrackerActivity(getApplicationContext());
           if (gps.canGetLocation()) {
        	   
        	   String phoneNumber=session.getPhoneNumber();

            // message with a google map location
            String message = "Comrade I am in trouble, a rapist trying to Force me into SHIT "
                    + Constants.GOOGLE_MAP_URL + "?q=<gps.getLatitude()>,<gps.getLongitude()>";

          //  String phoneNumber=session.getPhoneNumber();//pick the phone number stored in the shared preeferenes file

            for (int i = 0; i < contacts.length; i++) {
                String phone = contacts[i];
                //sending the message
                sendSMSMessage(phone, message);
            }//end the for

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("time_sent", getCurrentTimeStamp()));
            params.add(new BasicNameValuePair("content", message));
            params.add(new BasicNameValuePair("phone_number", phoneNumber));

            // getting JSON Object
            // Note that save message url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.URL_SAVE_MESSAGE,
                    "POST", params);


            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully sent the message
                    Intent intent = new Intent(getApplicationContext(), SMSActivity.class);
                    startActivity(intent);
                    Log.i("DB save", "Message saved successfully");

                    // closing this screen
                    finish();
                } else {
                    // failed to create contact
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //stop using the gps
            gps.stopUsingGPS();
        }
    }

}