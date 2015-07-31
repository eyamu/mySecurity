package com.bit15_21.helpers;

public class Constants
{
  public static final String BASE_URL = "http://hardgals.bugs3.com/".concat("mysecurity/");

  public static final String EMERGENCY_POLICE_NUMBER = "191";

    // File upload url
    public static final String FILE_UPLOAD_URL = BASE_URL.concat("android_connection/upload_recording.php");

    // url to create new user
    public static final String URL_CREATE_USER = BASE_URL.concat("android_connection/register_phone_user.php");
    // url to add mesaage to server
    public  static final String URL_SAVE_MESSAGE = BASE_URL.concat("android_connection/save_message.php");

    // url to add contact to server
    public  static final String URL_SAVE_CONTACT = BASE_URL.concat("android_connection/save_contacts.php");


    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Uploads";
    

    //shall be send to send map link to a contact
    public static final String GOOGLE_MAP_URL = "http://maps.google.com";

  public static String bindUrl(String paramString)
  {
    return BASE_URL.concat(paramString);
  }
}

