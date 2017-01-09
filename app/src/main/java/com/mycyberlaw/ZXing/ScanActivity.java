package com.mycyberlaw.ZXing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mycyberlaw.Config;
import com.mycyberlaw.R;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView matricNo;
    public static String scanContent;

    public static String JSON_store;

    private static final String JSON_ARRAY = "result";
    private static final String TAG_MATRICNUM = "matricNum";
    private static final String TAG_MARKS ="marks";
    private static final String TAG_REMARKS ="remarks";

    public static JSONArray marks = null;

    private int TRACK = 0;
    private TextView marksText;
    private TextView remarksText;

    Button prevBtn;
    Button nextBtn;
    ImageView pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Marks");

        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();

        matricNo = (TextView) findViewById(R.id.matricNo);
        marksText = (TextView) findViewById(R.id.marksText);
        remarksText = (TextView) findViewById(R.id.remarksText);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);

        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        pizza = (ImageView) findViewById(R.id.pizza);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult.getContents() != null) {
            Log.d("JSON", "scanningResult: " +scanningResult);
            //we have a result
            scanContent = scanningResult.getContents();

            matricNo.setText(scanContent);
            getJSON(Config.GET_MARKS);

        } else {
            pizza();
        }
    }

    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ScanActivity.this, null, "Please Wait...", true, true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri+"?matricNum="+scanContent);
                    Log.d("debug", "url marks "+url);
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

                parseJSON(JSON_store);
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    private void parseJSON(String store) {

        // parse JSON here
        // get String s from onPostExecute

        if(store != null) {
            nextBtn.setEnabled(true);

            extractJSON(store);

            showData();
        } else {
            pizza();
        }
    }

    private void extractJSON(String aStore) {
        try {
            marks = new JSONArray(aStore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showData() {

        try {

            // FIX THIS PART

            JSONObject jsonObject = marks.getJSONObject(TRACK);
            if (!marks.isNull(0)) {
                Log.d("JSON", "6");
                marksText.setText(jsonObject.getString(TAG_MARKS));
                remarksText.setText(jsonObject.getString(TAG_REMARKS));
            } else {
                if(TRACK<marks.length()){
                    TRACK++;
                }
                showData();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (v == nextBtn) {
            if(TRACK<marks.length()){

                if(TRACK==0)
                {
                    //Enable previous button because you are incrementing the count
                    prevBtn.setEnabled(true);
                }

                TRACK++;
                if(TRACK==(marks.length()-1))
                {
                    //Disable next button
                    v.setEnabled(false);
                }
            }
            showData();

        }
        if (v == prevBtn) {

            if(TRACK>0){

                if(TRACK==(marks.length()-1))
                {
                    //Enable next button because you are decrementing the count
                    nextBtn.setEnabled(true);
                }

                TRACK--;
                if(TRACK==0)
                {
                    //Disable previous button
                    v.setEnabled(false);
                }
            }
            showData();

        }
    }

    public void pizza() {
        matricNo.setVisibility(View.GONE);
        LinearLayout layout = (LinearLayout) findViewById(R.id.buttonLayout);
        for ( int i = 0; i < layout.getChildCount();  i++ ){
            View view = layout.getChildAt(i);
            view.setVisibility(View.GONE); // Or whatever you want to do with the view.
        }

        marksText.setVisibility(View.GONE);
        pizza.setVisibility(View.VISIBLE);
        remarksText.setText("But you got pizza. That's a good thing! :)");

        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        Snackbar
                .make(coordinatorLayoutView, "Nothing to show here :(", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", this)
                .show();
    }


}
