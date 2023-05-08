package com.androidpprog2.regals;

import android.app.Activity;
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

import java.util.UUID;

public class GiftHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private Gift gift;
    private TextView tvName;
    private TextView tvPrice;
    private  TextView tvLink;
    private TextView tvDate;
    private ImageView ivFoto;
    private TextView tvWishList;
    private TextView tvReserved;
    private TextView tvPrio;
    private TextView tvUser;
    //private ImageView ivFoto;
    private Activity activity;




    public GiftHolder(@NonNull View itemView, Gift gift, TextView tvName, TextView tvPrice,TextView tvLink, TextView tvDate,TextView tvWishlist, TextView tvReserved, ImageView ivFoto, Activity activity) {
        super(itemView);
        this.gift = gift;
        this.tvName = tvName;
        this.tvPrice = tvPrice;
        this.tvLink = tvLink;
        this.tvDate = tvDate;
        this.tvPrio = tvPrio;
        this.tvUser = tvUser;
        this.ivFoto = ivFoto;
        this.tvWishList = tvWishlist;
        this.tvReserved = tvReserved;

        this.activity = activity;
        //itemView.setOnClickListener(this);
    }


    public GiftHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.gift_object, parent, false));
        tvName = (TextView) itemView.findViewById(R.id.nameGift);
        tvPrice = (TextView) itemView.findViewById(R.id.priceGift);
        tvLink = (TextView) itemView.findViewById(R.id.linkGift);
        tvDate = (TextView) itemView.findViewById(R.id.dateGift);
        tvPrio = (TextView) itemView.findViewById(R.id.prioGift);
        tvUser = (TextView) itemView.findViewById(R.id.userGift);
        tvWishList = (TextView) itemView.findViewById(R.id.wishlistGift);
        tvReserved = (TextView) itemView.findViewById(R.id.reservedGift);
        ivFoto = (ImageView) itemView.findViewById(R.id.imageView);
        this.activity = activity;
        gift = new Gift();
        activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind(Gift gift) {
        this.gift = gift;
        tvName.setText(gift.getName());
        tvPrice.setText((int) gift.getPrice()); // no tinc clar lo de que sigui int ??
        tvLink.setText(gift.getLink());
        tvDate.setText(gift.getDate());
        tvPrio.setText(gift.getPrio());
        tvUser.setText(gift.getUser());
        tvWishList.setText(gift.getWishList());
        tvReserved.setText(gift.getReserved());// Faig que retorni int perque ho accepti com a text view, revisar si es pot mostrar d'altre forma o que faig
        //Potser seria millor idea fer aixi lo de reservat en comptes de fer un text view, revisar quan fem el get de la api
        //ivCapturat.setVisibility(pokemon.isCapturat() ? View.VISIBLE :
                //View.GONE);
       // ivFoto.setFoto(gift.ivFoto());
    }
    @Override
    public void onClick(View view) {

        UUID regalID = gift.getId();
        //Mirar be com poder pasar aixo a fragemnt, potser podem reutilitzar lo de la llista per fer-ho amb usuari i la resta de coses que calgui
        //Mirar si es necesari fer fragemnt o no cal, asegura't !
        //GiftListActivity fragment = new GiftListActivity();
        Bundle args = new Bundle();
        args.putSerializable("ARGUMENT_OBJECT_ID", regalID);
        //fragment.setArguments(args);
        FragmentManager fm = null;
        if (activity instanceof FragmentActivity) {
            fm = ((FragmentActivity) activity).getSupportFragmentManager();
        }
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
           // ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Toast.makeText(activity, "Error: No se pot obrir el fragment.", Toast.LENGTH_SHORT).show();
        }
    }


}



