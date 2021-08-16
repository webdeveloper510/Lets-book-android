package com.letsbook.letsbook.View.Inner_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.letsbook.letsbook.Adapters.Appointment_Details_Tab_Adapter;
import com.letsbook.letsbook.R;


public class OnClickRespond extends Fragment {


    public OnClickRespond() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_on_click_respond, container, false);


        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SERVICES"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEWS"));
        tabLayout.addTab(tabLayout.newTab().setText("PORTFOLIO"));
        tabLayout.addTab(tabLayout.newTab().setText("DETAILS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)view.findViewById(R.id.view_pager);
        Appointment_Details_Tab_Adapter appointmentDetailsTabAdapter = new Appointment_Details_Tab_Adapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(appointmentDetailsTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }




}