package com.mycyberlaw.student_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mycyberlaw.R;
import com.mycyberlaw.ZXing.ScanActivity;

public class SMarksActivity extends AppCompatActivity implements View.OnClickListener {

    // copy from MarksFragment

    Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smarks);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Marks");

        scanBtn = (Button) findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(SMarksActivity.this, ScanActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return true;
    }
}
