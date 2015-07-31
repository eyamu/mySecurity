package com.bit15_21.mysecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bit15_21.contacts.DatabaseHandler;
import com.bit15_21.contacts.DisplayHelper;

import java.util.ArrayList;


public class DisplayContactsActivity extends Activity {

    private DatabaseHandler mHelper;
    private SQLiteDatabase dataBase;

    private ArrayList<String> userId = new ArrayList<String>();
    private ArrayList<String> user_fName = new ArrayList<String>();
    private ArrayList<String> user_phone = new ArrayList<String>();

    private ListView userList;
    private AlertDialog.Builder build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);


        userList = (ListView) findViewById(R.id.List);

        mHelper = new DatabaseHandler(this);

        //add new record
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),
                        NewContact.class);
                startActivity(i);

            }
        });
        //long click to delete data
        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {

                build = new AlertDialog.Builder(DisplayContactsActivity.this);
                build.setTitle("Delete " + user_fName.get(arg2) + " "
                        + user_phone.get(arg2));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(
                                        getApplicationContext(),
                                        user_fName.get(arg2) + " "
                                                + user_phone.get(arg2)
                                                + " is deleted.", Toast.LENGTH_SHORT).show();

                                dataBase.delete(
                                        DatabaseHandler.TABLE_NAME,
                                        DatabaseHandler.KEY_ID + "="
                                                + userId.get(arg2), null);
                                displayData();
                                dialog.cancel();
                            }
                        });

                build.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DatabaseHandler.TABLE_NAME, null);

        userId.clear();
        user_fName.clear();
        user_phone.clear();
        if (mCursor.moveToFirst()) {
            do {
                userId.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_ID)));
                user_fName.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_FNAME)));
                user_phone.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_PHONE)));

            } while (mCursor.moveToNext());
        }
        DisplayHelper disadpt = new DisplayHelper(DisplayContactsActivity.this, userId, user_fName, user_phone);
        userList.setAdapter(disadpt);
        mCursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_contacts_activity, menu);
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
}
