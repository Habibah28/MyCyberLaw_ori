package com.mycyberlaw.chapter_manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycyberlaw.R;

/**
 * Created by Habibah on 02-Jun-16.
 */
public class Chapter3Notes extends Fragment {

    public Chapter3Notes() {
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
        return inflater.inflate(R.layout.chapter3_notes, container, false);
    }
}
