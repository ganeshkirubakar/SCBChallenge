package com.scbchallenge.view.mobilehome.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scbchallenge.R;
import com.scbchallenge.models.MobileList;
import com.scbchallenge.utils.AHMRealmUtil;
import com.scbchallenge.utils.AppConstants;
import com.scbchallenge.view.mobilehome.MobileListView;
import com.scbchallenge.view.mobilehome.interactor.MobileListInteractorImpl;
import com.scbchallenge.view.mobilehome.presenter.MobileListPresenter;
import com.scbchallenge.view.mobilehome.presenter.MobileListPresenterImpl;
import com.scbchallenge.view.mobilehome.ui.adapter.MobileListAdapter;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.AllMobileFragmentInterface;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.MobileListInterface;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.MobileViewInterface;

import java.util.List;

public class AllMobileFragment extends Fragment implements MobileListView, MobileListInterface, AllMobileFragmentInterface {

    private RecyclerView mMobileList;
    private MobileListPresenter mobileListPresenter;
    private ProgressBar progressBar;
    private MobileViewInterface mobileViewInterface;
    private List<MobileList> items;
    private boolean updateTable = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.mobile_list_view, container, false);
        mMobileList = (RecyclerView) mView.findViewById(R.id.mobile_list);
        progressBar = (ProgressBar) mView.findViewById(R.id.progress);
        mobileListPresenter = new MobileListPresenterImpl(this, new MobileListInteractorImpl());
        MobileListFragment.setAllMobileFragmentInterfaceInterface(this);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AHMRealmUtil.getInstance(getActivity().getApplicationContext()).getMobileList().size() == 0) {
            mobileListPresenter.onResume();
        } else {
            updateTable = false;
            items = AHMRealmUtil.getInstance(getActivity().getApplicationContext()).getMobileList();
            setItems(items);
        }
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setItems(List<MobileList> items) {
        this.items = items;
        if (updateTable) {
            AHMRealmUtil.getInstance(getActivity().getApplicationContext()).addMobileList(items);
        }
        MobileListAdapter mobileListAdapter = new MobileListAdapter(getActivity(), items, false);
        mobileListAdapter.setMobileListInterface(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMobileList.setLayoutManager(llm);
        mMobileList.setAdapter(mobileListAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(int position) {
        MobileDetailFragment mobileDetailFragment = new MobileDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.MOBILE_NAME, items.get(position).getName());
        bundle.putString(AppConstants.MOBILE_DESCRIPTION, items.get(position).getDescription());
        bundle.putString(AppConstants.PRICE_VALUE, items.get(position).getPrice());
        bundle.putString(AppConstants.RATING_VALUE, items.get(position).getRating());
        bundle.putString(AppConstants.IMAGE_URL, items.get(position).getId());
        mobileDetailFragment.setArguments(bundle);
        mobileViewInterface.loadFragment(mobileDetailFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mobileViewInterface = (MobileViewInterface) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mobileViewInterface = (MobileViewInterface) activity;
    }

    @Override
    public void sortBy(String key, int id) {
        items = (AHMRealmUtil.getInstance(getContext()).sortListBy(key, id, false));
        updateTable = false;
        setItems(items);
    }

    @Override
    public void notifyData() {
        updateTable = false;
        items = AHMRealmUtil.getInstance(getActivity().getApplicationContext()).getMobileList();
        setItems(items);
    }
}
