package com.mycyberlaw;

/**
 * Created by Habibah on 12-Jul-16.
 */
public class Config {
    //URL to our login.php file
    public static final String STUD_LOGIN_URL = "http://habibah.xyz/mycyberlaw/login.php";
    public static final String LEC_LOGIN_URL = "http://habibah.xyz/mycyberlaw/lecLogin.php";
    public static final String REGISTER_URL = "http://habibah.xyz/mycyberlaw/register.php";
    public static final String GET_MESSAGE_URL = "http://habibah.xyz/mycyberlaw/getMsg.php";
    public static final String SEND_MESSAGE_URL = "http://habibah.xyz/mycyberlaw/sendMsg.php";
    public static final String GET_MATRICNUM = "http://habibah.xyz/mycyberlaw/getMatricNum.php";
    public static final String SAVE_MARKS = "http://habibah.xyz/mycyberlaw/saveMarks.php";
    public static final String GET_MARKS = "http://habibah.xyz/mycyberlaw/retrieve.php";
    public static final String GET_PROFILE = "http://habibah.xyz/mycyberlaw/getProfile.php";
    public static final String SAVE_PROFILE = "http://habibah.xyz/mycyberlaw/saveProfile.php";
    public static final String SAVE_PROFILE_LEC = "http://habibah.xyz/mycyberlaw/saveProfileLec.php";
    public static final String GET_PROFILE_LEC = "http://habibah.xyz/mycyberlaw/getProfileLec.php";
    public static final String UPLOAD_NOTES_URL = "http://habibah.xyz/mycyberlaw/uploadNotes.php";
    public static final String UPLOAD_ASSIGN_URL = "http://habibah.xyz/mycyberlaw/uploadAssign.php";

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 1;
    public static final String JSON_ARRAY = "result";
    public static final int TRACK = 0;

    public static String LOG = "log";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_MATRICNUM = "matricNum";
    public static final String KEY_STAFFID = "staffID";
    public static final String KEY_PASS = "pass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "MyCyberLaw";

    //This would be used to store the email of current logged in user
    public static final String MATRICNUM_SHARED_PREF = "matricNum";
    public static final String STAFFID_SHARED_PREF = "staffID";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    // send and receive  message
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_MSG = "msg";
    public static final String KEY_SENDER = "name";
    public static final String KEY_RECORD = "record";

    // save marks
    public static final String KEY_MATRICNUM_MARKS = "matricNum";
    public static final String KEY_MARKS = "marks";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_STAFFID_MARKS = "staffID";

    // registration
    public static final String KEY_NAME = "name";
    public static final String KEY_MATRICNUM_REG = "matricNum";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS_REG = "pass";

    // get and save profile
    public static final String KEY_NAME_PROFILE = "name";
    public static final String KEY_EMAIL_PROFILE = "email";
    public static final String KEY_PASSWORD = "pass";
    public static final String KEY_MATRIC_NO = "matricNum";
    public static final String KEY_STAFF_ID = "staffID";
}
