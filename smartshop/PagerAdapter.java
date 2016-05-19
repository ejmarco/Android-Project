package com.example.erikk.smartshop;

/**
 * Created by Erikk on 19/02/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                VistaTiendas tab1 = new VistaTiendas();
                return tab1;
            case 1:
                VistaAll tab3 = new VistaAll();
                return tab3;
            case 2:
                VistaAll tab4 = new VistaAll();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}