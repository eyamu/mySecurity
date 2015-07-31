package com.bit15_21.mysecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bit15_21.contacts.DatabaseHandler;
import com.bit15_21.helpers.Constants;
import com.bit15_21.helpers.JSONParser;
import com.bit15_21.helpers.SessionManager;
import com.bit15_21.record.CaptureAudioActivity;
import com.bit15_21.services.SMSActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewContact extends Activity implements View.OnClickListener {

    private ProgressDialog pDialog;

    SMSActivity sms = new SMSActivity();

    ImageView home;
    ImageView message;
    ImageView mic;
    ImageView call;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser = new JSONParser();
    SessionManager session;
   //create object of the index activity to be used for making a call
    IndexActivity homeObj=new IndexActivity();
    
    private Button btn_save;
    private EditText edit_name, edit_phone;
    private DatabaseHandler mHelper;
    private SQLiteDatabase dataBase;
    private String fname, phone,userPhoneNumber;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.layout_new_contact);

        btn_save = (Button) findViewById(R.id.save);
        edit_name = (EditText) findViewById(R.id.et_name);
        edit_phone = (EditText) findViewById(R.id.et_phone);
        
        
        
        
        this.home=(ImageView)findViewById(R.id.set_ico_img_home);
        this.message=(ImageView)findViewById(R.id.set_ico_img_msg);
        this.mic=(ImageView)findViewById(R.id.set_ico_img_mic);
        this.call=(ImageView)findViewById(R.id.set_ico_img_call);
        


        // button click event
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (edit_name.getText().toString().trim().length() > 0 
                		&& edit_name.getText().toString().trim().length() > 0) {
                // creating new Contact
                new AddContact().execute();
              }
                else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewContact.this);
                    alertBuilder.setTitle("Invalid Data");
                    alertBuilder.setMessage("Please, Enter valid data");
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertBuilder.create().show();
                }

            }
        });
        mHelper = new DatabaseHandler(this);


        this.home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent home = new Intent(NewContact.this,
                        IndexActivity.class);
                NewContact.this.startActivity(home);
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
                Intent recordingIntent = new Intent(getApplicationContext(), CaptureAudioActivity.class);
                NewContact.this.startActivity(recordingIntent);
            }
        });
        
        this.call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
               //make a call
            	homeObj.makeCall(Constants.EMERGENCY_POLICE_NUMBER);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.new_contact, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

    @Override
    public void onClick(View v) {

    }

    class AddContact extends AsyncTask<String, String, String> {
        boolean failure = false;

        
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewContact.this);
            pDialog.setMessage("Adding contact.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... paramVarArgs) {
        	
        	session=new SessionManager(getApplicationContext());
        	
            fname = edit_name.getText().toString().trim();
            phone = edit_phone.getText().toString().trim();
          
            userPhoneNumber=session.getPhoneNumber();
            
            //save into the sQL Lite Database too
            saveData();
         Log.i(getLocalClassName(),"SAVED DATA");

                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("name",fname));
                params.add(new BasicNameValuePair("phone",phone));
                params.add(new BasicNameValuePair("phone_number", userPhoneNumber));

                // getting JSON Object
                // Note that save message url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(Constants.URL_SAVE_CONTACT,
                        "POST", params);


                // check for success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // successfully added the contact
                        Intent intent = new Intent(getApplicationContext(), NewContact.class);
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

            return null;
        }

        protected void onPostExecute(String paramString) {
            NewContact.this.pDialog.dismiss();
            if (paramString != null) {
                Toast.makeText(NewContact.this, paramString, Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * save data into SQLite
     */
    private void saveData() {
        dataBase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHandler.KEY_FNAME, fname);
        values.put(DatabaseHandler.KEY_PHONE, phone);

        System.out.println("");
        //insert data into database
        dataBase.insert(DatabaseHandler.TABLE_NAME, null, values);        //close database
        dataBase.close();
        finish();


    }
}
