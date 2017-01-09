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
import com.mycyberlaw.lecturer_manager.LMarksActivity;
import com.mycyberlaw.lecturer_manager.LMessageActivity;
import com.mycyberlaw.lecturer_manager.LProfileActivity;
import com.mycyberlaw.lecturer_manager.UploadAssignmentActivity;
import com.mycyberlaw.lecturer_manager.UploadNotesActivity;
import com.mycyberlaw.login_manager.LoginActivity;

public class LecturerFragment extends Fragment implements View.OnClickListener {

    LinearLayout loginLayout;
    Button buttonLogin;

    LinearLayout lecturerLayout;
    Button buttonProfile;
    Button buttonMessages;
    Button buttonMarks;
    Button buttonUploadNotes;
    Button buttonUploadAssignment;

    public LecturerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lecturer, container, false);

        loginLayout = (LinearLayout) rootView.findViewById(R.id.loginLayout);
        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);

        lecturerLayout = (LinearLayout) rootView.findViewById(R.id.lecturerLayout);
        buttonProfile = (Button) rootView.findViewById(R.id.buttonProfile);
        buttonMessages = (Button) rootView.findViewById(R.id.buttonMessages);
        buttonMarks = (Button) rootView.findViewById(R.id.buttonMarks);
        buttonUploadNotes = (Button) rootView.findViewById(R.id.buttonUploadNotes);
        buttonUploadAssignment = (Button) rootView.findViewById(R.id.buttonUploadAssignment);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String prefStaffID = sharedPreferences.getString(Config.STAFFID_SHARED_PREF,"Not Available");

        String log = sharedPreferences.getString(Config.LOG, "Not Available");

        Log.d("debug", "Config.LOG = "+Config.LOG);
        Log.d("debug", "log String "+log);

        if (prefStaffID.contains("Not Available")  || log.equals("lecturer out") || log.equals("student in")) {
            lecturerLayout.setVisibility(View.GONE);
            Log.d("debug", "lecturer layout gone");
        } else if (!prefStaffID.contains("Not Available") || log.equals("student out") || log.equals("lecturer in")) {
            loginLayout.setVisibility(View.GONE);
            Log.d("debug", "login layout gone");
        }

        buttonLogin.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);
        buttonMessages.setOnClickListener(this);
        buttonMarks.setOnClickListener(this);
        buttonUploadNotes.setOnClickListener(this);
        buttonUploadAssignment.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.buttonLogin:
                intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent.putExtra("role", "lecturer");

                // log student out
                SharedPreferences preferences = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(Config.MATRICNUM_SHARED_PREF, "");

                // update log
                editor.putString(Config.LOG, "student out");

                //Saving the sharedpreferences
                editor.commit();

                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonProfile:
                intent = new Intent(getActivity().getApplicationContext(), LProfileActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonMessages:
                intent = new Intent(getActivity().getApplicationContext(), LMessageActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonMarks:
                intent = new Intent(getActivity().getApplicationContext(), LMarksActivity.class);
                startActivity(intent);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.buttonUploadNotes:
                intent = new Intent(getActivity().getApplicationContext(), UploadNotesActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonUploadAssignment:
                intent = new Intent(getActivity().getApplicationContext(), UploadAssignmentActivity.class);
                startActivity(intent);
        }
    }
}
