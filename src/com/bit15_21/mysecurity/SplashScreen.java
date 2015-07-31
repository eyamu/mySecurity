package com.bit15_21.mysecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.bit15_21.helpers.Constants;
import com.bit15_21.helpers.SessionManager;

public class SplashScreen
        extends Activity {
    private static int SPLASH_TIME_OUT = 1000;// 1 Second

    SessionManager session;//to check if the user already registered

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.splash_screen_layout);
        new Handler().postDelayed(new Runnable() {
            public void run() {
            	
            	 /*  Intent localIntent = new Intent(SplashScreen.this, IndexActivity.class);
                   SplashScreen.this.startActivity(localIntent);

                 SplashScreen.this.finish();
*/
                // Session manager
               session = new SessionManager(getApplicationContext());

                // Check if user is already registered for the first time with the app
                if (session.isRegistered()==true) {
                    //redirect to the IndexActivity page

                    Intent localIntent = new Intent(SplashScreen.this, IndexActivity.class);
                    SplashScreen.this.startActivity(localIntent);

                }

              else {

                    Intent registerIntent = new Intent(SplashScreen.this, UserRegistrationActivity.class);
                    SplashScreen.this.startActivity(registerIntent);

                }
                SplashScreen.this.finish();
              

            }
        }, SPLASH_TIME_OUT);
    }
}


