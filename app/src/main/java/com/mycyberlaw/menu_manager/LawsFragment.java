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
import com.mycyberlaw.law_manager.Law1;
import com.mycyberlaw.law_manager.Law10;
import com.mycyberlaw.law_manager.Law11;
import com.mycyberlaw.law_manager.Law12;
import com.mycyberlaw.law_manager.Law2;
import com.mycyberlaw.law_manager.Law3;
import com.mycyberlaw.law_manager.Law4;
import com.mycyberlaw.law_manager.Law5;
import com.mycyberlaw.law_manager.Law6;
import com.mycyberlaw.law_manager.Law7;
import com.mycyberlaw.law_manager.Law8;
import com.mycyberlaw.law_manager.Law9;

public class LawsFragment extends Fragment implements View.OnClickListener {

    Button law1;
    Button law2;
    Button law3;
    Button law4;
    Button law5;
    Button law6;
    Button law7;
    Button law8;
    Button law9;
    Button law10;
    Button law11;
    Button law12;

    public LawsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_laws, container, false);

        law1 = (Button) rootView.findViewById(R.id.law1);
        law1.setOnClickListener(this);

        law2 = (Button) rootView.findViewById(R.id.law2);
        law2.setOnClickListener(this);

        law3 = (Button) rootView.findViewById(R.id.law3);
        law3.setOnClickListener(this);

        law4 = (Button) rootView.findViewById(R.id.law4);
        law4.setOnClickListener(this);

        law5 = (Button) rootView.findViewById(R.id.law5);
        law5.setOnClickListener(this);

        law6 = (Button) rootView.findViewById(R.id.law6);
        law6.setOnClickListener(this);

        law7 = (Button) rootView.findViewById(R.id.law7);
        law7.setOnClickListener(this);

        law8 = (Button) rootView.findViewById(R.id.law8);
        law8.setOnClickListener(this);

        law9 = (Button) rootView.findViewById(R.id.law9);
        law9.setOnClickListener(this);

        law10 = (Button) rootView.findViewById(R.id.law10);
        law10.setOnClickListener(this);

        law11 = (Button) rootView.findViewById(R.id.law11);
        law11.setOnClickListener(this);

        law12 = (Button) rootView.findViewById(R.id.law12);
        law12.setOnClickListener(this);

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

        Intent i;

        switch (v.getId()) {
            case R.id.law1:
                i = new Intent(getActivity(), Law1.class);
                startActivity(i);
                break;

            case R.id.law2:
                i = new Intent(getActivity(), Law2.class);
                startActivity(i);
                break;

            case R.id.law3:
                i = new Intent(getActivity(), Law3.class);
                startActivity(i);
                break;

            case R.id.law4:
                i = new Intent(getActivity(), Law4.class);
                startActivity(i);
                break;

            case R.id.law5:
                i = new Intent(getActivity(), Law5.class);
                startActivity(i);
                break;

            case R.id.law6:
                i = new Intent(getActivity(), Law6.class);
                startActivity(i);
                break;

            case R.id.law7:
                i = new Intent(getActivity(), Law7.class);
                startActivity(i);
                break;

            case R.id.law8:
                i = new Intent(getActivity(), Law8.class);
                startActivity(i);
                break;

            case R.id.law9:
                i = new Intent(getActivity(), Law9.class);
                startActivity(i);
                break;

            case R.id.law10:
                i = new Intent(getActivity(), Law10.class);
                startActivity(i);
                break;

            case R.id.law11:
                i = new Intent(getActivity(), Law11.class);
                startActivity(i);
                break;

            case R.id.law12:
                i = new Intent(getActivity(), Law12.class);
                startActivity(i);
                break;
        }
    }
}