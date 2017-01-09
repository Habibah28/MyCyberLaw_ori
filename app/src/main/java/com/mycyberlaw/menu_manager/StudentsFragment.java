package com.mycyberlaw.menu_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mycyberlaw.Config;
import com.mycyberlaw.R;
import com.mycyberlaw.login_manager.LoginActivity;
import com.mycyberlaw.student_manager.SMarksActivity;
import com.mycyberlaw.student_manager.SMessageActivity;
import com.mycyberlaw.student_manager.SProfileActivity;

public class StudentsFragment extends Fragment implements View.OnClickListener {

    LinearLayout loginLayout;
    Button buttonLogin;

    LinearLayout studentLayout;
    Button buttonProfile;
    Button buttonMessages;
    Button buttonMarks;

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        loginLayout = (LinearLayout) rootView.findViewById(R.id.loginLayout);
        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);

        studentLayout = (LinearLayout) rootView.findViewById(R.id.studentlayout);
        buttonProfile = (Button) rootView.findViewById(R.id.buttonProfile);
        buttonMessages = (Button) rootView.findViewById(R.id.buttonMessages);
        buttonMarks = (Button) rootView.findViewById(R.id.buttonMarks);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String prefMatricNum = sharedPreferences.getString(Config.MATRICNUM_SHARED_PREF,"Not Available");

        String log = sharedPreferences.getString(Config.LOG, "Not Available");

        Log.d("debug", "Config.LOG = "+Config.LOG);
        Log.d("debug", "log String "+log);

        if (prefMatricNum.contains("Not Available") || log.equals("student out") || log.equals("lecturer in")) {
            studentLayout.setVisibility(View.GONE);
            Log.d("debug", "student layout gone");
        } else if (!prefMatricNum.contains("Not Available") || log.equals("lecturer out")|| log.equals("student in")) {
            loginLayout.setVisibility(View.GONE);
            Log.d("debug", "login layout gone");
        }

        buttonLogin.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);
        buttonMessages.setOnClickListener(this);
        buttonMarks.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.buttonLogin:
                Log.d("debug", "login");
                intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent.putExtra("role", "student");

                // log lecturer out
                SharedPreferences preferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
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

                startActivityForResult(intent, Config.REQUEST_CODE);
                break;
            case R.id.buttonProfile:
                intent = new Intent(getActivity().getApplicationContext(), SProfileActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonMessages:
                intent = new Intent(getActivity().getApplicationContext(), SMessageActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonMarks:
                intent = new Intent(getActivity().getApplicationContext(), SMarksActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
        }
    }
}
