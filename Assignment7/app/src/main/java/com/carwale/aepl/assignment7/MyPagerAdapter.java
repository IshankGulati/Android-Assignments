package com.carwale.aepl.assignment7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by aepl on 1/7/16.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    public MyPagerAdapter(FragmentManager fm, int ntabs) {
        super(fm);
        numberOfTabs = ntabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
