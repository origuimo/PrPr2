package com.androidpprog2.regals;

import static com.androidpprog2.regals.LogIn.token;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.porfile);
        Button btnProfile = findViewById(R.id.button6);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        ImageButton btnBack = findViewById(R.id.imageButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Feed.class);
                startActivity(intent);
            }
        });
        Integer id = getID();
        //makeRequest(id);


    }

    public Integer getID(){
        Integer id = 0;

        return id;
    }

/*
    public void makeRequest(Integer id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/{id]";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Verificar el código de respuesta antes de procesar la respuesta
                        int statusCode = response.optInt("code", -1); // Obtener el código de respuesta desde la respuesta JSON

                        switch (statusCode) {
                            case 200:
                                try {
                                    JSONArray jsonArray = response.getJSONArray("gifts");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject giftJson = jsonArray.getJSONObject(i);
                                        int id = giftJson.getInt("id");
                                        int wishlistId = giftJson.getInt("wishlist_id");
                                        String productUrl = giftJson.getString("product_url");
                                        int priority = giftJson.getInt("priority");
                                        boolean booked = giftJson.getBoolean("booked");
                                        //Gift gift = new Gift(id, wishlistId, productUrl, priority, booked);
                                        //giftList.add(gift);
                                    }

                                   // giftAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            case 401:
                                Log.e("Error", "Usuari no autoritzat");
                                break;
                            case 500:
                                Log.e("Error", "Error getting list of all users");
                                break;
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }
    public void makeRequestWishlist(Integer id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/{id]/wishlists";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Verificar el código de respuesta antes de procesar la respuesta
                        int statusCode = response.optInt("code", -1); // Obtener el código de respuesta desde la respuesta JSON

                        switch (statusCode) {
                            case 200:
                                try {
                                    JSONArray jsonArray = response.getJSONArray("gifts");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject giftJson = jsonArray.getJSONObject(i);
                                        int id = giftJson.getInt("id");
                                        int wishlistId = giftJson.getInt("wishlist_id");
                                        String productUrl = giftJson.getString("product_url");
                                        int priority = giftJson.getInt("priority");
                                        boolean booked = giftJson.getBoolean("booked");

                                    }



                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            case 401:
                                Log.e("Error", "Usuari no autoritzat");
                                break;
                            case 500:
                                Log.e("Error", "Error getting list of all users");
                                break;
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }

 */


}