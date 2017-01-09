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
import com.mycyberlaw.chapter_manager.Chapter1Activity;
import com.mycyberlaw.chapter_manager.Chapter2Activity;
import com.mycyberlaw.chapter_manager.Chapter3Activity;
import com.mycyberlaw.chapter_manager.Chapter4Activity;

/**
 * Created by Habibah on 21-Jun-16.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    Button chapter1;
    Button chapter2;
    Button chapter3;
    Button chapter4;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        chapter1 = (Button) rootView.findViewById(R.id.chapter1);
        chapter1.setOnClickListener(this);

        chapter2 = (Button) rootView.findViewById(R.id.chapter2);
        chapter2.setOnClickListener(this);

        chapter3 = (Button) rootView.findViewById(R.id.chapter3);
        chapter3.setOnClickListener(this);

        chapter4 = (Button) rootView.findViewById(R.id.chapter4);
        chapter4.setOnClickListener(this);

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
            case R.id.chapter1:
                i = new Intent(getActivity(), Chapter1Activity.class);
                startActivity(i);
                break;

            case R.id.chapter2:
                i = new Intent(getActivity(), Chapter2Activity.class);
                startActivity(i);
                break;

            case R.id.chapter3:
                i = new Intent(getActivity(), Chapter3Activity.class);
                startActivity(i);
                break;

            case R.id.chapter4:
                i = new Intent(getActivity(), Chapter4Activity.class);
                startActivity(i);
                break;
        }
    }
}