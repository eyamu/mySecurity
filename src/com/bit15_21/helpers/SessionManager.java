package com.bit15_21.helpers;

/**
 * Created by eyamu on 5/9/15.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    //file for shared prefernaces;
    public static  final String PREFERENCE_KEY_FILE ="com.bit15_21.mysecurity.PREFERENCE_FILE_KEY";

    private static final String KEY_IS_LOGGEDIN = "isFirstTime";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFERENCE_KEY_FILE, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setRegistrationStatus(boolean isFirstTime) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isFirstTime);

        // commit changes
        editor.commit();

        Log.d(TAG, "User Regisered");
    }



    public boolean isRegistered(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setPhone(String phone) {

        editor.putString(KEY_PHONE_NUMBER, phone);

        // commit changes
        editor.commit();

        Log.d(TAG, "Phone Captured");
    }

    public String getPhoneNumber(){
        return pref.getString(KEY_PHONE_NUMBER, null);
    }
}