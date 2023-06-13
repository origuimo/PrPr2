package com.androidpprog2.regals;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.GiftViewHolder> {
    private Context context;
    private List<Gift> giftList;

    public GiftAdapter(Context context, List<Gift> giftList) {
        this.context = context;
        this.giftList = giftList;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gift_object, parent, false);
        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        Gift gift = giftList.get(position);

        holder.idTextView.setText(String.valueOf(gift.getId()));
        holder.wishlistIdTextView.setText(String.valueOf(gift.getWishlistId()));
        holder.productUrlTextView.setText(gift.getProductUrl());
        holder.priorityTextView.setText(String.valueOf(gift.getPriority()));
        holder.bookedTextView.setText(String.valueOf(gift.isBooked()));
    }

    @Override
    public int getItemCount() {
        return giftList.size();
    }

    public static class GiftViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView wishlistIdTextView;
        TextView productUrlTextView;
        TextView priorityTextView;
        TextView bookedTextView;

        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlistIdTextView = itemView.findViewById(R.id.wishlistGift);
            productUrlTextView = itemView.findViewById(R.id.url);
            priorityTextView = itemView.findViewById(R.id.priority);
            bookedTextView = itemView.findViewById(R.id.booked);
        }
    }
}