package com.mycyberlaw;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Habibah on 02-Jul-16.
 */
public class RegisterMenu extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText matricNumEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private Button registerBtn;

    private static final int CAMERA_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_register);

        requestCameraPermission();

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        matricNumEditText = (EditText) findViewById(R.id.matricNumEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(this);
    }

    private void registerUser(){

        final ProgressDialog loading = ProgressDialog.show(RegisterMenu.this, "Please Wait...", null, true, true);

        final String name = nameEditText.getText().toString().trim();
        final String matricNum = matricNumEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String pass = passwordEditText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        snackBar(response);
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        snackBar(error.toString());
                        loading.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Config.KEY_NAME, name);
                params.put(Config.KEY_MATRICNUM_REG, matricNum);
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASS_REG, pass);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())
                && !nameEditText.getText().toString().isEmpty()
                && !matricNumEditText.getText().toString().isEmpty()
                && !emailEditText.getText().toString().isEmpty()
                && !passwordEditText.getText().toString().isEmpty()
                && !confirmPasswordEditText.getText().toString().isEmpty()) {
            registerUser();
        } else {
            // if password not match or fields empty
            if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                nameEditText.setError("Required");
            }

            if (TextUtils.isEmpty(matricNumEditText.getText().toString())) {
                matricNumEditText.setError("Required");
            }

            if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                emailEditText.setError("Required");
            }

            if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
                passwordEditText.setError("Required");
            }

            if (TextUtils.isEmpty(confirmPasswordEditText.getText().toString())) {
                confirmPasswordEditText.setError("Password not match");
            }
        }
    }

    private void snackBar(String msg) {
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        Snackbar
                .make(coordinatorLayoutView, msg, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", null)
                .show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    //Requesting permission
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == CAMERA_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}
