package com.mycyberlaw.student_manager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mycyberlaw.AdapterMsg;
import com.mycyberlaw.Config;
import com.mycyberlaw.R;
import com.mycyberlaw.DataMsg;

public class SMessageActivity extends AppCompatActivity {

    RecyclerView msgView;
    AdapterMsg adapterMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smessage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Message");

        loadAll(Config.GET_MESSAGE_URL);
    }

    private void loadAll(String url) {

        class AllGroups extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SMessageActivity.this, null, "Please Wait...",true,true);
                loading.setCancelable(false);
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

                    Log.d("debug", "sb "+sb.toString());
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
                parseJSON(s);
            }
        }
        AllGroups gj = new AllGroups();
        gj.execute(url);
    }

    private void parseJSON(String data) {
        // extract JSON
        List<DataMsg> results = new ArrayList<>();

        try {
            JSONArray msg = new JSONArray(data);

            for (int index = 0; index < msg.length(); index++) {
                JSONObject msgJSON = msg.getJSONObject(index);
                DataMsg dataMsg = new DataMsg();
                dataMsg.subject = msgJSON.getString(Config.KEY_SUBJECT);
                dataMsg.msg = msgJSON.getString(Config.KEY_MSG);
                dataMsg.name = msgJSON.getString(Config.KEY_SENDER);
                dataMsg.record = msgJSON.getString(Config.KEY_RECORD);
                results.add(dataMsg);
            }

            msgView = (RecyclerView) findViewById(R.id.msgView);
            adapterMsg = new AdapterMsg(this, results);
            msgView.setAdapter(adapterMsg);
            msgView.setLayoutManager(new LinearLayoutManager(this));

            msgView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("debug", "error "+e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return true;
    }
}
