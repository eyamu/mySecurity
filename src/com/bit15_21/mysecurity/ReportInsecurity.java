package com.bit15_21.mysecurity;

import com.bit15_21.services.SMSActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Background Async Task to Load all contacts by making HTTP Request
 */
public class ReportInsecurity extends AsyncTask<String, String, String> {
	private Context context;
    // Progress Dialog
    private ProgressDialog pDialog;
    SMSActivity sms=new SMSActivity();
    
	
	public ReportInsecurity(Context context) 
	{
	    this.context = context;
	   
	}


    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Reporting....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        //pDialog.show();
    }

    /**
     * 
     */
    protected String doInBackground(String... args) {
        // Building Parameters
       sms.report();


        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * *
     */
    protected void onPostExecute(String nn) {
        // dismiss the dialog saving the message into the db
        pDialog.dismiss();

    }


}
