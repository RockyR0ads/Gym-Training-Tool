package com.example.gymtrainingtool.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gymtrainingtool.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

//    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
//    private final Context mContext;
    private int noOfTabs;

    public SectionsPagerAdapter(FragmentManager fm,int tabs) {
        super(fm);
//        mContext = context;
        this.noOfTabs= tabs;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.

               switch(position){
                   case 0:
                       return new Calculator();
                   case 1:
                       return new GripLogger();
                    default:
                           return null;
               }
    }


    @Override
    public int getCount() {
        // Show 2 total pages.
        return noOfTabs;
    }
}