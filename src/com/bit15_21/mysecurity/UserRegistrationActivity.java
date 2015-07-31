package com.bit15_21.mysecurity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bit15_21.helpers.Constants;
import com.bit15_21.helpers.JSONParser;
import com.bit15_21.helpers.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserRegistrationActivity extends Activity {


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText firstname, lastname, phone;
    RadioGroup gender;
    RadioButton male, female;


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

  SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Edit Text
        firstname = (EditText) findViewById(R.id.et_firstname);
        lastname = (EditText) findViewById(R.id.et_lastname);
        phone = (EditText) findViewById(R.id.et_phone);

        gender = (RadioGroup) findViewById(R.id.rg_gender);
        male = (RadioButton) findViewById(R.id.rb_male);
        female = (RadioButton) findViewById(R.id.rb_female);

        // Create button
        Button registerUser = (Button) findViewById(R.id.btn_register_user);

        // button click event
        registerUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating sign up new user
                new SignUp().execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Background Async Task to sign up user for this app
     */
    class SignUp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserRegistrationActivity.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating New User
         */
        protected String doInBackground(String... args) {
            String fName = firstname.getText().toString();
            String lName = lastname.getText().toString();
            String pNumber = phone.getText().toString();

            int selectedId = gender.getCheckedRadioButtonId();

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if (selectedId == male.getId()) {
                String gender = (String) male.getText();//pick the text of the gender

                // Building Parameters
                params.add(new BasicNameValuePair("firstname", fName));
                params.add(new BasicNameValuePair("lastname", lName));
                params.add(new BasicNameValuePair("gender", gender));
                params.add(new BasicNameValuePair("phone_number", pNumber));
            } else {
                String gender = (String) female.getText();//pick the text of the gender

                // Building Parameters
                params.add(new BasicNameValuePair("firstname", fName));
                params.add(new BasicNameValuePair("lastname", lName));
                params.add(new BasicNameValuePair("gender", gender));
                params.add(new BasicNameValuePair("phone_number", pNumber));

            }
            // getting JSON Object
            // Note that create user url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(Constants.URL_CREATE_USER, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // user successfully registered sucessfully
                   //store the status in the Shared Preferences file
                    session.setRegistrationStatus(true);

                    //store the phone number in the Shared Preferences file
                    session.setPhone(pNumber);
                    // successfully registered user
                    Intent i = new Intent(getApplicationContext(), IndexActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create user
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
