package com.androidpprog2.regals;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftHolder> {
    private List<Gift> lgifts;
    private Activity activity;

    public GiftAdapter(List<Gift> lgifts, Activity activity) {
        this.lgifts = lgifts;
        this.activity = activity;
    }

    @Override
    public GiftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.gift_list, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.2);
        view.setLayoutParams(layoutParams);
        return new GiftHolder(layoutInflater, parent, activity);
    }

    @Override
    public void onBindViewHolder(GiftHolder holder, int position) {
        Gift gift = lgifts.get(position);
        holder.bind(gift);
    }

    @Override
    public int getItemCount() {
        return lgifts.size();
    }

    public void updateUI() {
        //GiftListActivity giftListActivity = GiftListActivity.getInstance(activity);
       // lgifts = giftListActivity.getGifts();
        notifyDataSetChanged();
    }
}
