package com.mycyberlaw.lecturer_manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycyberlaw.Config;
import com.mycyberlaw.R;

public class LMarksActivity extends AppCompatActivity {

    public static String JSON_store;
    private static final String JSON_ARRAY = "result";
    public static JSONArray matrics = null;
    private int TRACK = 0;

    List<String> matricArray = new ArrayList<String>();
    public TextView[] textView;
    public EditText[] editText;

    EditText editTextRemarks;
    LinearLayout matricLayout;
    LinearLayout marksLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lmarks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Students' Marks");

        editTextRemarks = (EditText) findViewById(R.id.editTextRemarks);
        matricLayout = (LinearLayout) findViewById(R.id.matricLayout);
        marksLayout = (LinearLayout) findViewById(R.id.marksLayout);

        // get list of matric numbers of students
        getJSON(Config.GET_MATRICNUM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSave) {
            saveData();
        } else {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LMarksActivity.this, "Please Wait...", null, true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
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
                // textViewJSON.setText(s);
                JSON_store = s;

                extractJSON(JSON_store);
                showData();
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    private void extractJSON(String aStore) {
        try {
            // add matric number from JSON array into List array
            JSONObject jsonObject = new JSONObject(aStore);
            matrics = jsonObject.getJSONArray(JSON_ARRAY);
            for (int i = 0; i < matrics.length(); i++) {
                matricArray.add(matrics.getJSONObject(i).getString("matricNum"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showData() {
        if (!matricArray.isEmpty()) {

            // view matric number into textview
            textView = new TextView[matricArray.size()];
            editText = new EditText[matricArray.size()];
            for(int i = 0; i < matricArray.size(); i++ )
            {
                textView[i] = new TextView(this);
                textView[i].setText(matricArray.get(i));
                textView[i].setTextSize(16);
                textView[i].setPadding(0, 30, 0, 30);
                textView[i].setTypeface(null, Typeface.BOLD);
                matricLayout.addView(textView[i]);
            }

            // generate edittext for each matric number
            for (int j = 0; j < matricArray.size(); j++) {
                editText[j] = new EditText(this);
                editText[j].setId(Integer.parseInt(matricArray.get(j)));
                editText[j].setHint("Marks");
                editText[j].setTextSize(16);
                marksLayout.addView(editText[j]);
            }
        }

    }

    private void saveData() {

        final String remarks = editTextRemarks.getText().toString();
        final String staffID = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(Config.STAFFID_SHARED_PREF,"Not Available");
        final ProgressDialog loading = ProgressDialog.show(LMarksActivity.this, "Please Wait...", null, true, true);
        final int loop = editText.length;

        if (remarks.isEmpty()) {
            editTextRemarks.setError("Required");
        } else {
            for (int k = 0; k < editText.length; k++) {
                final String marks;
                final String matric;

                if (!editText[k].getText().toString().isEmpty()) {
                    marks = editText[k].getText().toString();
                    matric = textView[k].getText().toString();

                    Log.d("debug", "textView "+matric);
                    Log.d("debug", "marks "+marks);
                    Log.d("debug", "remarks "+remarks);
                    Log.d("debug", "Config.STAFFID_SHARED_PREF "+ staffID);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SAVE_MARKS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (loop == editText.length) {
                                        loading.dismiss();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loading.dismiss();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put(Config.KEY_MATRICNUM_MARKS, matric);
                            params.put(Config.KEY_MARKS, marks);
                            params.put(Config.KEY_REMARKS, remarks);
                            params.put(Config.KEY_STAFFID_MARKS, staffID);
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }
            }
        }


    }
}
