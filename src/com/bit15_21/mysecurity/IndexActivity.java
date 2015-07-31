package com.bit15_21.mysecurity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bit15_21.helpers.Constants;
import com.bit15_21.helpers.JSONParser;
import com.bit15_21.record.*;
import com.bit15_21.services.SMSActivity;

public  class IndexActivity extends Activity implements OnClickListener {


    JSONParser jsonParser = new JSONParser();//JSon Object
    SMSActivity sms;
    CaptureAudioActivity audio;

    private Button launch_button;
    private Button settings;

    Button dailBtn;
    ImageView home;
    ImageView message;
    ImageView mic;
    ImageView call;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.layout_index);
        //requestWindowFeature(1);

        this.launch_button = ((Button) findViewById(R.id.launch_button));
        this.settings = ((Button) findViewById(R.id.settings_button));
        
        
        this.home=(ImageView)findViewById(R.id.set_ico_img_home);
        this.message=(ImageView)findViewById(R.id.set_ico_img_msg);
        this.mic=(ImageView)findViewById(R.id.set_ico_img_mic);
        this.call=(ImageView)findViewById(R.id.set_ico_img_call);
        

        this.launch_button.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View paramAnonymousView) {
            	
                // Send messages to numbers in the background
                new ReportInsecurity(getApplicationContext()).execute();

            	        /*Make a phone send Messages*/
                makeCall(Constants.EMERGENCY_POLICE_NUMBER);
               
                 
            }
        });
        
        
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
            	 //redirect to add new contacts
                Intent settings = new Intent(getApplicationContext(), NewContact.class);
                IndexActivity.this.startActivity(settings);
            }
        });


        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                //redirect to home activity
                Intent home = new Intent(getApplicationContext(), IndexActivity.class);
                IndexActivity.this.startActivity(home);
            }
        });

        this.message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                //send Messages
                new ReportInsecurity(getApplicationContext()).execute();
            }
        });


        this.mic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                //start recording and Uploading
                //redirect to start upload activity
                Intent recordingIntent = new Intent(getApplicationContext(),CaptureAudioActivity.class);
                IndexActivity.this.startActivity(recordingIntent);
            }
        });
        this.call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                //make a emergency phone call
                makeCall(Constants.EMERGENCY_POLICE_NUMBER);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.index, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

	
	 //Method to make an emergency  phone call
    protected void makeCall(String phone) {
        Log.i("Make call", "");

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse(phone));

        try {
            startActivity(phoneIntent);
            finish();
            Log.i("Finish call.", "Finished making a calll");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "Call failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
