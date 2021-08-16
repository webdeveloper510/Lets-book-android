package com.letsbook.letsbook.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.letsbook.letsbook.View.Inner_Fragments.Details;
import com.letsbook.letsbook.View.Inner_Fragments.Portfolio;
import com.letsbook.letsbook.View.Inner_Fragments.Reviews;
import com.letsbook.letsbook.View.Inner_Fragments.Services;

public class Booking_list_adapter extends FragmentStatePagerAdapter {
    int numberofTabs;
    public Booking_list_adapter(FragmentManager fragmentManager, int numbersofTabs){

        super(fragmentManager);
        this.numberofTabs = numbersofTabs;
    }
    @Override
    public int getCount() {
        return numberofTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                Services services = new Services();
                return services;
            case 1:
                Reviews reviews = new Reviews();
                return reviews;
            case 2:
                Portfolio portfolio = new Portfolio();
                return portfolio;
            case 3:
                Details details = new Details();
                return details;
            default:
                return null;
        }
    }
}
