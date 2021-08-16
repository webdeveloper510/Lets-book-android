package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.letsbook.letsbook.R;
import com.letsbook.letsbook.View.NavigationFragments.About_us_Fragment;


public class TermsAndConditions extends Fragment {

    private ImageView termAndConditionBack;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);


        termAndConditionBack = view.findViewById(R.id.term_and_condition_back);
        termAndConditionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(TermsAndConditions.this);
                fragmentTransaction.commit();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}