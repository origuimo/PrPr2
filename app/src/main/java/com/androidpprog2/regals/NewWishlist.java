package com.androidpprog2.regals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewWishlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wishlist);

        Button crear = findViewById(R.id.button4);
        EditText nom = findViewById(R.id.editText6);
        EditText descripcio = findViewById(R.id.editText7);
        EditText end_date = findViewById(R.id.editText9);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noms = nom.getText().toString();
                String descr = descripcio.getText().toString();
                String data = end_date.getText().toString();

                createWishlist(noms, descr, data);

                Intent intent = new Intent(NewWishlist.this, Wishlist.class);
                startActivity(intent);
            }
        });
    }

    public void createWishlist(String nom, String descripcio, String data) {
        JSONObject credentials = new JSONObject();

        try {
            credentials.put("name", nom);
            credentials.put("description", descripcio);
            credentials.put("end_date", data);
        } catch (JSONException e) {
            Log.e("Error", "Error al agregar los valores al objeto JSON");
        }

        String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/wishlists";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta exitosa aquí
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de solicitud aquí
                        Log.e("Error", "Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
