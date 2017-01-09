package com.mycyberlaw.menu_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mycyberlaw.R;
import com.mycyberlaw.ZXing.ScanActivity;

public class MarksFragment extends Fragment implements View.OnClickListener {

    Button scanBtn;

    public MarksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marks, container, false);

        scanBtn = (Button) rootView.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), ScanActivity.class));
    }
}
