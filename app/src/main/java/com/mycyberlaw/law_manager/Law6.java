package com.mycyberlaw.law_manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mycyberlaw.R;

/**
 * Created by Habibah on 04-Jun-16.
 */
public class Law6 extends AppCompatActivity {

    // private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.law6);

        // toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MCMC Act");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }
}
