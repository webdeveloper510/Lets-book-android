package com.letsbook.letsbook.View.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.letsbook.letsbook.R;
import com.letsbook.letsbook.Storage.SharedPrefManager;
import com.letsbook.letsbook.View.Activitys.Login;
import com.letsbook.letsbook.View.Inner_Fragments.ChangePassword;
import com.letsbook.letsbook.View.Inner_Fragments.TermsAndConditions;

public class About_us_Fragment extends Fragment {


    private RelativeLayout clickOn_TermAndCondition, changePassword, logout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_about_us_, container, false);


        clickOn_TermAndCondition = view.findViewById(R.id.clickOn_term_and_condition);
        changePassword = view.findViewById(R.id.change_password);
        logout = view.findViewById(R.id.logout);





        clickOn_TermAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TermsAndConditions());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ChangePassword());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getContext()).clear();
                Intent intent = new Intent(view.getContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });






        return view;
    }

}