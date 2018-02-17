package com.scbchallenge.view.mobilehome.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scbchallenge.R;
import com.scbchallenge.utils.AHMRealmUtil;
import com.scbchallenge.utils.AppConstants;
import com.scbchallenge.utils.CustomDialog;
import com.scbchallenge.utils.CustomViewPager;
import com.scbchallenge.utils.PagerSlidingTabStrip;
import com.scbchallenge.view.mobilehome.ui.adapter.MobileListPagerAdapter;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.AllMobileFragmentInterface;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.DialogInterface;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.FavouriteFragmentInterface;

import java.util.ArrayList;


public class MobileListFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener, DialogInterface {
    private static FavouriteFragmentInterface favouriteFragmentInterface;
    private static AllMobileFragmentInterface allMobileFragmentInterface;
    private CustomViewPager mViewPager;
    private PagerSlidingTabStrip tabsStrip;
    private int position = -1;

    public static void setFavouriteInterface(FavouriteFragmentInterface favouriteInterface) {
        favouriteFragmentInterface = favouriteInterface;
    }

    public static void setAllMobileFragmentInterfaceInterface(AllMobileFragmentInterface allFragmentInterface) {
        allMobileFragmentInterface = allFragmentInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mMobileList = inflater.inflate(R.layout.mobile_base, container, false);
        mViewPager = (CustomViewPager) mMobileList.findViewById(R.id.viewpager);
        tabsStrip = (PagerSlidingTabStrip) mMobileList.findViewById(R.id.tabs);
        ImageView mSort = (ImageView) mMobileList.findViewById(R.id.sort);
        mSort.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
        return mMobileList;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeViewPager();
    }

    private void initializeViewPager() {
        MobileListPagerAdapter mobileListPagerAdapter = new MobileListPagerAdapter(getChildFragmentManager());
        ArrayList<String> tabTitles = new ArrayList<>();
        tabTitles.add(getString(R.string.all_mobile_tab));
        AllMobileFragment allMobileFragment = new AllMobileFragment();
        mobileListPagerAdapter.addTab(allMobileFragment);
        tabTitles.add(getString(R.string.favourite_tab));
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        mobileListPagerAdapter.addTab(favouriteFragment);
        mViewPager.setAdapter(mobileListPagerAdapter);
        mobileListPagerAdapter.addTitle(tabTitles);
        mViewPager.setCurrentItem(getArguments().getInt(AppConstants.ON_PAGE_SELECTED));
        mViewPager.setPagingEnabled(false);
        tabsStrip.setViewPager(mViewPager);
        tabsStrip.setTextColor(R.color.white);
        onPageSelected(getArguments().getInt(AppConstants.ON_PAGE_SELECTED));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Handle OnPageScrolled
    }

    @Override
    public void onPageSelected(int position) {
        tabsStrip.setTabPosition(position);
        this.position = position;
        if (position == 1) {
            favouriteFragmentInterface.setFavouriteItems(AHMRealmUtil.getInstance(getContext()).getFavouriteList());
        } else if (position == 0
                && allMobileFragmentInterface != null) {
            allMobileFragmentInterface.notifyData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Handle OnScrollStateChanged
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sort) {
            CustomDialog customDialog = new CustomDialog(getActivity());
            customDialog.setDialogInterface(this);
            customDialog.show();
            customDialog.setCancelable(false);
        }
    }

    @Override
    public void sortBy(int id) {
        if (id == AppConstants.LOW_TO_HIGH
                || id == AppConstants.HIGH_TO_LOW) {
            if (position == 0) {
                allMobileFragmentInterface.sortBy(AppConstants.PRICE_SORT, id);
            } else {
                favouriteFragmentInterface.sortBy(AppConstants.PRICE_SORT, id);
            }
        } else if (id == AppConstants.RATING) {
            if (position == 0) {
                allMobileFragmentInterface.sortBy(AppConstants.RATING_SORT, id);
            } else {
                favouriteFragmentInterface.sortBy(AppConstants.RATING_SORT, id);
            }
        }
    }
}
