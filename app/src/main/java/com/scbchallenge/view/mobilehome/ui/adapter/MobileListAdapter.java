package com.scbchallenge.view.mobilehome.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scbchallenge.R;
import com.scbchallenge.models.MobileList;
import com.scbchallenge.utils.AHMRealmUtil;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.MobileListInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MobileListAdapter extends RecyclerView.Adapter<MobileListAdapter.MobileListViewHolder> {
    private Context mContext;
    private List<MobileList> mobileLists;
    private MobileListInterface mobileListInterface;
    private boolean fromFavourite = false;

    public MobileListAdapter(Context context, List<MobileList> mobileLists, boolean fromFavourite) {
        mContext = context;
        this.mobileLists = mobileLists;
        this.fromFavourite = fromFavourite;
    }

    @Override
    public MobileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.mobile_list_item, parent, false);
        return new MobileListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobileListViewHolder holder, final int position) {
        holder.mobileName.setText(mobileLists.get(position).getName());
        holder.mobileDescription.setText(mobileLists.get(position).getDescription());
        holder.priceValue.setText(String.format(mContext.getString(R.string.price), mobileLists.get(position).getPrice()));
        holder.ratingValue.setText(String.format(mContext.getString(R.string.rating), mobileLists.get(position).getRating()));
        if (mobileLists.get(position).isFavourite()) {
            holder.favourites.setBackground(mContext.getDrawable(R.drawable.btn_rating_star_on_normal_holo_dark));
        } else {
            holder.favourites.setBackground(mContext.getDrawable(R.drawable.btn_rating_star_off_disabled_focused_holo_dark));
        }
        if (!fromFavourite) {
            holder.favourites.setVisibility(View.VISIBLE);
            holder.favourites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileLists.get(position).isFavourite()) {
                        mobileLists.get(position).setFavourite(false);
                        AHMRealmUtil.getInstance(mContext).updateFavouriteMobileList(false, mobileLists.get(position).getId());
                    } else {
                        mobileLists.get(position).setFavourite(true);
                        AHMRealmUtil.getInstance(mContext).updateFavouriteMobileList(true, mobileLists.get(position).getId());
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.favourites.setVisibility(View.GONE);
        }
        Picasso.with(mContext).load(mobileLists.get(position).getThumbImageURL()).placeholder(mContext.getDrawable(R.drawable.launcher)).into(holder.mobileImage);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobileListInterface != null) {
                    mobileListInterface.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mobileLists.size();
    }

    public void setMobileListInterface(MobileListInterface mobileListInterface) {
        this.mobileListInterface = mobileListInterface;
    }

    class MobileListViewHolder extends RecyclerView.ViewHolder {
        private TextView mobileName;
        private TextView mobileDescription;
        private TextView priceValue;
        private TextView ratingValue;
        private ImageView mobileImage;
        private ImageView favourites;
        private LinearLayout itemLayout;

        MobileListViewHolder(View itemView) {
            super(itemView);
            mobileName = (TextView) itemView.findViewById(R.id.mobile_name);
            mobileDescription = (TextView) itemView.findViewById(R.id.mobile_description);
            priceValue = (TextView) itemView.findViewById(R.id.price_label);
            ratingValue = (TextView) itemView.findViewById(R.id.rating_label);
            mobileImage = (ImageView) itemView.findViewById(R.id.mobile_image);
            favourites = (ImageView) itemView.findViewById(R.id.favourite);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
        }
    }
}
