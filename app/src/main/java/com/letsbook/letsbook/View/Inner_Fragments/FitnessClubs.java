package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.letsbook.letsbook.R;


public class FitnessClubs extends Fragment {

    public FitnessClubs() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fitness_clubs, container, false);

        

        return view;
    }
}