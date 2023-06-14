package com.androidpprog2.regals;

import static org.json.JSONObject.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewGift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_gift);

        Button create = (Button) findViewById(R.id.button3);
        //Button loadImage = (Button) findViewById(R.id.button5);
        Button edit = (Button) findViewById(R.id.button4);

        EditText nom = (EditText) findViewById(R.id.editText6);
        EditText descripcio = (EditText) findViewById(R.id.editText7);
        EditText link = (EditText) findViewById(R.id.editText9);
        EditText price = (EditText) findViewById(R.id.editText8);
        EditText category = (EditText) findViewById(R.id.editText10);
        create.setMovementMethod(LinkMovementMethod.getInstance());
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noms = nom.getText().toString();
                String descripcion = descripcio.getText().toString();
                String enllaç = link.getText().toString();
                String preu = price.getText().toString();
                String categoria = category.getText().toString();

                postNewGift(noms, descripcion, enllaç, preu, categoria);
                //Intent intent = new Intent(LogIn.this, Feed.class);
                //startActivity(intent);
            }
        });
    }

    public void postNewGift(String nom, String descripcio, String link, String price, String category) {

        JSONObject credentials = new JSONObject();

        try {
            credentials.put("name", nom);
            credentials.put("description", descripcio);
            credentials.put("link", link);
            credentials.put("photo", link);
            credentials.put("price", price);
            credentials.put("categoryIds", category);
        } catch (Exception e) {
            Log.e("Error", "Hi ha hagut un error afegint els valors al JSON object ");
        }

        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int statusCode = 0;
                        try {
                            statusCode = response.getInt("statusCode");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        switch (statusCode) {
                            case 201:
                                try {
                                    JSONObject gift = new JSONObject();
                                    int id = response.getInt("id");

                                    try {
                                        gift.put("whislist_id", NULL);
                                        gift.put("product_url", "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + id);
                                        gift.put("priority", NULL);
                                    } catch (Exception e) {
                                        Log.e("Error", "Hi ha hagut un error afegint els valors al JSON object ");
                                    }

                                    String url = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/gifts";
                                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, gift,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    int statusCode = 0;
                                                    try {
                                                        statusCode = response.getInt("statusCode");
                                                    } catch (JSONException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    switch (statusCode) {
                                                        case 201:
                                                            Log.e("Respuesta", "New gift created in SocialGift");
                                                            break;
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    NetworkResponse networkResponse = error.networkResponse;
                                                    if (networkResponse != null && networkResponse.data != null) {
                                                        JSONObject errorResponse = null;
                                                        try {
                                                            errorResponse = new JSONObject(new String(networkResponse.data));
                                                        } catch (JSONException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                        int statusCode = errorResponse.optInt("statusCode");
                                                        switch (statusCode) {
                                                            case 400:
                                                                Log.e("Respuesta", "Error: Bad Request ");
                                                                break;
                                                            case 406:
                                                                Log.e("Respuesta", "Error: Missing parameters ");
                                                                break;
                                                            case 409:
                                                                Log.e("Respuesta", "Error: Conflicto al crear producto");
                                                                break;
                                                            case 500:
                                                                Log.e("Respuesta", "Error: Product not created ");
                                                                break;
                                                            case 502:
                                                                Log.e("Respuesta", "Error: Internal Server Error");
                                                                break;
                                                            default:
                                                                Log.e("Respuesta", "Código de respuesta desconocido: " + statusCode);
                                                                break;
                                                        }
                                                    }
                                                }
                                            });

                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            JSONObject errorResponse = null;
                            try {
                                errorResponse = new JSONObject(new String(networkResponse.data));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            int statusCode = errorResponse.optInt("statusCode");
                            switch (statusCode) {
                                case 400:
                                    Log.e("Respuesta", "Error: Bad Request ");
                                    break;
                                case 406:
                                    Log.e("Respuesta", "Error: Missing parameters ");
                                    break;
                                case 409:
                                    Log.e("Respuesta", "Error: Conflicto al crear producto");
                                    break;
                                case 500:
                                    Log.e("Respuesta", "Error: Product not created ");
                                    break;
                                case 502:
                                    Log.e("Respuesta", "Error: Internal Server Error");
                                    break;
                                default:
                                    Log.e("Respuesta", "Código de respuesta desconocido: " + statusCode);
                                    break;
                            }
                        }
                    }
                });

    // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

















