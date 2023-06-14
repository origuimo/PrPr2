package com.androidpprog2.regals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wishlist extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        Spinner spinner = findViewById(R.id.spinner);
        Button nova = findViewById(R.id.button);
        List<String> spinnerItems;
        String token = LogIn.getToken();
        spinnerItems = getWishlists(token);
        spinnerItems.add("Wishlists:");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        nova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wishlist.this, NewWishlist.class);
                startActivity(intent);
            }
        });
    }

    public List<String> getWishlists(String token) {

        List<String> categoryNames = new ArrayList<>();

        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject category = response.getJSONObject(i);
                        String name = category.getString("name");
                        categoryNames.add(name);
                    }
                } catch (JSONException e) {
                    Log.e("Error", "Error al procesar la respuesta JSON");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar errores de solicitud aquí
                Log.e("Error", "Error en la solicitud: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + token); // Asegúrate de agregar un espacio después de "Bearer"
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        return categoryNames;
    }
}
