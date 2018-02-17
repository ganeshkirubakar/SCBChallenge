package com.scbchallenge.view.mobilehome.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.scbchallenge.R;
import com.scbchallenge.models.MobileList;
import com.scbchallenge.utils.AHMRealmUtil;
import com.scbchallenge.view.mobilehome.ui.adapter.MobileListAdapter;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.FavouriteFragmentInterface;

import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteFragmentInterface {

    private RecyclerView mFavouriteList;
    private ProgressBar progressBar;
    private List<MobileList> items;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.mobile_list_view, container, false);
        mFavouriteList = (RecyclerView) mView.findViewById(R.id.mobile_list);
        progressBar = (ProgressBar) mView.findViewById(R.id.progress);
        MobileListFragment.setFavouriteInterface(this);
        return mView;
    }

    @Override
    public void setFavouriteItems(List<MobileList> mobileList) {
        progressBar.setVisibility(View.VISIBLE);
        items = mobileList;
        MobileListAdapter mobileListAdapter = new MobileListAdapter(getActivity(), mobileList, true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mFavouriteList.setLayoutManager(llm);
        mFavouriteList.setAdapter(mobileListAdapter);
        progressBar.setVisibility(View.GONE);
        deleteItem();
    }

    @Override
    public void sortBy(String key, int id) {
        items = (AHMRealmUtil.getInstance(getContext()).sortListBy(key, id, true));
        setFavouriteItems(items);
    }

    private void deleteItem() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                String name = items.get(position).getId();
                AHMRealmUtil.getInstance(getContext()).deleteFavouriteListItem(name);
                items.remove(position);
                setFavouriteItems(items);
            }
        });
        touchHelper.attachToRecyclerView(mFavouriteList);
    }
}
