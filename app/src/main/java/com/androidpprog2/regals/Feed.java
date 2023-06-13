package com.androidpprog2.regals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feed extends AppCompatActivity {

    private List<Gift> giftList;
    private GiftAdapter giftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Botons
        ImageButton regal = findViewById(R.id.regal_img);
        regal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GiftListActivity.class); // frame_layout == llista regals ??
                startActivity(intent);
            }
        });

        ImageButton persones = findViewById(R.id.persones);
        persones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class); //S'ha de canviar a la pantalla d'amics quan l'haguem creat
                startActivity(intent);
            }
        });

        ImageButton newGift = findViewById(R.id.mas);
        newGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewGift.class); //S'ha de canviar a la pantalla d'amics quan l'haguem creat
                startActivity(intent);
            }
        });
/*
        ImageButton persones = findViewById(R.id.persones);
        persones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class); //S'ha de canviar a la pantalla d'amics quan l'haguem creat
                startActivity(intent);
            }
        });

 */

        giftList = new ArrayList<>();
        giftAdapter = new GiftAdapter(this,giftList);
        recyclerView.setAdapter(giftAdapter);

        makeRequest();
    }
    public void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("gifts");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject giftJson = jsonArray.getJSONObject(i);
                                int id = giftJson.getInt("id");
                                int wishlistId = giftJson.getInt("wishlist_id");
                                String productUrl = giftJson.getString("product_url");
                                int priority = giftJson.getInt("priority");
                                boolean booked = giftJson.getBoolean("booked");

                                Gift gift = new Gift(id, wishlistId, productUrl, priority, booked);
                                giftList.add(gift);
                            }

                            giftAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(jor);
    }

}