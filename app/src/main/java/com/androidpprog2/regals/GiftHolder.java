package com.androidpprog2.regals;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.regals.Gift;
import com.androidpprog2.regals.R;

import java.util.List;
import java.util.UUID;
public class GiftHolder extends RecyclerView.ViewHolder {
    TextView idTextView;
    TextView wishlistIdTextView;
    TextView productUrlTextView;
    TextView priorityTextView;
    TextView bookedTextView;

    public GiftHolder(@NonNull View itemView) {
        super(itemView);

        wishlistIdTextView = itemView.findViewById(R.id.wishlistGift);
        productUrlTextView = itemView.findViewById(R.id.url);
        priorityTextView = itemView.findViewById(R.id.priority);
        bookedTextView = itemView.findViewById(R.id.booked);
    }
}

