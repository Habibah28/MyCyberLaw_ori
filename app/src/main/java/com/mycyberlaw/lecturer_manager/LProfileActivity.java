package com.mycyberlaw.lecturer_manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.mycyberlaw.AboutMenu;
import com.mycyberlaw.Config;
import com.mycyberlaw.MainActivity;
import com.mycyberlaw.R;

public class LProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName;
    TextView textViewStaffID;

    LinearLayout viewModeLayout;
    TextView textViewEmail;

    LinearLayout editModeLayout;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPass;

    Button buttonSave;

    public static JSONArray profile = null;
    String staffID;
    String emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lprofile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        //Initializing textview
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewStaffID = (TextView) findViewById(R.id.textViewStaffID);

        viewModeLayout = (LinearLayout) findViewById(R.id.viewModeLayout);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        staffID = sharedPreferences.getString(Config.STAFFID_SHARED_PREF,"Not Available");

        if (!staffID.isEmpty() || !staffID.equals("Not Available")) {
            //Showing the current logged in email to textview
            textViewStaffID.setText(staffID);

            getProfile(Config.GET_PROFILE_LEC);
        } else {
            viewModeLayout.setVisibility(View.GONE);
            new MaterialDialog.Builder(this)
                    .title("Error 404")
                    .content("No profile found at the moment. Please try again!")
                    .positiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO
                            onBackPressed();
                        }
                    })
                    .show();
        }
    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.STAFFID_SHARED_PREF, "");

                        // update log
                        editor.putString(Config.LOG, "lecturer out");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(LProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        } else if (id == R.id.action_edit) {
            editProfile();
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutMenu.class));
            return true;
        } else {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        // popup confirm changes

        saveProfile();
    }

    private void editProfile() {
        editModeLayout = (LinearLayout) findViewById(R.id.editModeLayout);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPass = (EditText) findViewById(R.id.editTextConfirmPass);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        editTextEmail.setText(emailString);

        editModeLayout.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
        viewModeLayout.setVisibility(View.GONE);
        buttonSave.setOnClickListener(this);
    }

    private void getProfile(String url) {
        class GetProfile extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LProfileActivity.this, null,"Please Wait...",true,true);
                //loading.setCancelable(false);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri+"?staffID="+staffID);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // show data in editText fields from parseJSON
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    profile = jsonObject.getJSONArray(Config.JSON_ARRAY);
                    jsonObject = profile.getJSONObject(Config.TRACK);

                    textViewName.setText(jsonObject.getString(Config.KEY_NAME_PROFILE));
                    textViewEmail.setText(jsonObject.getString(Config.KEY_EMAIL_PROFILE));

                    emailString = jsonObject.getString(Config.KEY_EMAIL_PROFILE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetProfile gp = new GetProfile();
        gp.execute(url);
    }

    private void saveProfile() {
        final String email = editTextEmail.getText().toString();
        final String pass1 = editTextPassword.getText().toString();
        String pass2 = editTextConfirmPass.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(LProfileActivity.this, null, "Please Wait...", true, true);

        if (!pass1.equals(pass2) || pass1.isEmpty() || pass2.isEmpty() || email.isEmpty()) {
            editTextEmail.setError("Required");
            editTextPassword.setError("Password not match");
            editTextConfirmPass.setError("Password not match");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SAVE_PROFILE_LEC,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog(response);
                            loading.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog(error.toString());
                            loading.dismiss();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(Config.KEY_EMAIL_PROFILE, email);
                    params.put(Config.KEY_PASSWORD, pass1);
                    params.put(Config.KEY_STAFF_ID, staffID);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void dialog(String msg) {
        new MaterialDialog.Builder(this)
                .content(msg)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(intent);
                    }
                })
                .show();
    }
}
