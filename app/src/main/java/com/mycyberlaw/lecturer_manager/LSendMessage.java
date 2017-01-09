package com.mycyberlaw.lecturer_manager;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

public class LSendMessage extends AppCompatActivity {

    EditText editTextMsg;
    EditText editTextSubject;

    String msgString;
    String subjectString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsend_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Message");

        editTextMsg = (EditText) findViewById(R.id.editTextMessage);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            case R.id.menuSend:
                sendMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendMessage() {

        final ProgressDialog loading = ProgressDialog.show(this, "Sending message...", null, true, true);

        msgString = editTextMsg.getText().toString().replace(" ", "%20");
        subjectString = editTextSubject.getText().toString().replace(" ", "%20");

        if (msgString.isEmpty() || subjectString.isEmpty()) {
            editTextMsg.setError("Required");
            editTextSubject.setError("Required");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SEND_MESSAGE_URL,
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
                    params.put(Config.KEY_SUBJECT, subjectString);
                    params.put(Config.KEY_MSG, msgString);
                    params.put(Config.KEY_STAFFID, Config.STAFFID_SHARED_PREF);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send, menu);
        return true;
    }

    private void snackBar(String msg) {
        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        Snackbar
                .make(coordinatorLayoutView, msg, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", (View.OnClickListener) this)
                .show();
    }
}
