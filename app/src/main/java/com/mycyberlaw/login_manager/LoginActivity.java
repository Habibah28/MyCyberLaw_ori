package com.mycyberlaw.login_manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.mycyberlaw.Config;
import com.mycyberlaw.R;
import com.mycyberlaw.RegisterMenu;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUser;
    EditText editTextPassword;
    Button buttonLogin;
    TextView linkSignup;

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        linkSignup = (TextView) findViewById(R.id.linkSignup);

        buttonLogin.setOnClickListener(this);
        linkSignup.setOnClickListener(this);
    }

    private void login(){
        //Getting values from edit texts
        final String user = editTextUser.getText().toString().trim();
        final String pass = editTextPassword.getText().toString().trim();

        final String data = getIntent().getExtras().getString("role");

        final ProgressDialog loading;
        loading = ProgressDialog.show(this, null, "Loading...", true, true);
        loading.setCancelable(false);

        // if lecturer
        if (data.equals("lecturer") && !data.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LEC_LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //If we are getting success from server
                    if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                        //Creating a shared preference

                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.STAFFID_SHARED_PREF, user);

                        editor.putString(Config.LOG, "lecturer in");

                        //Saving values to editor
                        editor.apply();

                        //Starting profile activity
                        loading.dismiss();
                        Intent intent = new Intent();
                        setResult(Config.RESULT_CODE, intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Invalid ID or password", Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_STAFFID, user);
                    params.put(Config.KEY_PASS, pass);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        // if student
        else if (data.equals("student") && !data.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.STUD_LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //If we are getting success from server
                    if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                        //Creating a shared preference

                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                        editor.putString(Config.MATRICNUM_SHARED_PREF, user);

                        editor.putString(Config.LOG, "student in");

                        //Saving values to editor
                        editor.apply();

                        //Starting profile activity
                        loading.dismiss();
                        Intent intent = new Intent();
                        setResult(Config.RESULT_CODE, intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        //If the server response is not success
                        //Displaying an error message on toast
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Invalid ID or password", Toast.LENGTH_LONG).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put(Config.KEY_MATRICNUM, user);
                    params.put(Config.KEY_PASS, pass);

                    //returning parameter
                    return params;
                }
            };

            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.buttonLogin:
                login();
                break;
            case R.id.linkSignup:
                startActivity(new Intent(this, RegisterMenu.class));
                break;
        }
    }
}
