package com.androidpprog2.regals;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

public class viewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    private JSONObject user;
    private EditText name;
    private EditText lastName;
    private EditText password;
    private EditText email;
    private ImageView image;
    private Activity activity;

    public viewHolder(@NonNull View itemView, JSONObject user, EditText name, EditText lastName, EditText email, EditText password, ImageView image, Activity activity) {
        super(itemView);
        this.user = user;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.image = image;


        this.activity = activity;
        itemView.setOnClickListener(this);
    }


    public viewHolder(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        super(inflater.inflate(R.layout.gift_object, parent, false));
        name = (EditText) itemView.findViewById(R.id.nameGift);
        lastName = (EditText) itemView.findViewById(R.id.priceGift);
        email = (TextView) itemView.findViewById(R.id.linkGift);
        password = (TextView) itemView.findViewById(R.id.dateGift);
        image = (ImageView) itemView.findViewById(R.id.imageView);
        this.activity = activity;
        user = new JSONObject();
        activity = activity;
        itemView.setOnClickListener(this);
    }

    public void bind(JSONObject user) {
        this.user = user;
        name.setText(user.getString());
        lastName.setText(user.getString());
        email.setText(user.getString());
        password.setText(user.getString());
        //Image
        user = user;
    }
}



