package com.freedoctorhelpline.chatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 4/28/2017.
 */

public class PagerAdaptor extends FragmentStatePagerAdapter {
    private String[] titles = { "Item 1", "Item 2", "Item 3" };
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public PagerAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        switch(position){
//            case 0:{
//                return new AllFragments();
//            }
//            case 1:{
//                return new BlankFragment();
//            }
//            case 2:{
//                return new FeedbackFragment2();
//            }
//        }
        mFragmentList.get(position);


        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
