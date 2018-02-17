package com.scbchallenge.view.mobilehome.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MobileListPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTabContentTitle;

    private Fragment mPrimaryItem;

    public MobileListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addTab(Fragment frag) {
        mFragments.add(frag);
    }

    public void addTitle(ArrayList<String> title) {
        mTabContentTitle = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentStatePagerAdapter#setPrimaryItem(ViewGroup, int, Object)
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position,
                               Object object) {
        super.setPrimaryItem(container, position, object);
        mPrimaryItem = (Fragment) object;
    }

    /**
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getItemPosition(Object)
     */
    @Override
    public int getItemPosition(Object object) {
        if (object == mPrimaryItem) {
            return POSITION_UNCHANGED;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabContentTitle.get(position);
    }
}
