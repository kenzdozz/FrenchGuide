package com.ahtaya.chidozie.frenchguide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by CHIDOZIE on 12/27/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return new NumbersFragment();
            case 1: return new SentencesFragment();
            case 2: return new ColorsFragment();
            case 3: return new PhrasesFragment();
            default: return new NumbersFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Numbers";
            case 1: return "Sentences";
            case 2: return "Color";
            case 3: return "Phrases";
            default: return "Numbers";
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
