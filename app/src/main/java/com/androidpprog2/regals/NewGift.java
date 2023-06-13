package com.androidpprog2.regals;

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
                                    int id = response.getInt("id");
                                    String name = response.getString("name");
                                    String description = response.getString("description");
                                    String link = response.getString("link");
                                    String photo = response.getString("photo");
                                    double price = response.getDouble("price");
                                    int categoryIds = response.getInt("categoryIds");

                                    // Manejar la respuesta con código de respuesta 201 aquí
                                    // Por ejemplo, puedes mostrar los datos recibidos en la interfaz de usuario
                                    Log.d("Respuesta", "ID: " + id);
                                    Log.d("Respuesta", "Nombre: " + name);
                                    Log.d("Respuesta", "Descripción: " + description);
                                    Log.d("Respuesta", "Enlace: " + link);
                                    Log.d("Respuesta", "Foto: " + photo);
                                    Log.d("Respuesta", "Precio: " + price);
                                    Log.d("Respuesta", "ID de categoría: " + categoryIds);
                                    Snackbar.make(findViewById(R.id.new_giftLayout), "Tot ok", Snackbar.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            case 400:
                                try {
                                    boolean expose = response.getBoolean("expose");
                                    int statusc = response.getInt("statusCode");
                                    int status = response.getInt("status");
                                    JSONObject body = response.getJSONObject("body");
                                    String type = response.getString("type");
                                    String error = response.getString("error");

                                    // Manejar la respuesta con código de respuesta 400 aquí
                                    // Por ejemplo, puedes mostrar el mensaje de error en la interfaz de usuario
                                    Log.e("Respuesta", "Exponer: " + expose);
                                    Log.e("Respuesta", "Código de estado: " + statusc);
                                    Log.e("Respuesta", "Estado: " + status);
                                    Log.e("Respuesta", "Tipo: " + type);
                                    Log.e("Respuesta", "Error: " + error);

                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            case 406:
                            case 409:
                            case 500:
                                try {
                                    boolean success = response.getBoolean("success");
                                    JSONObject errorObject = response.getJSONObject("error");
                                    String errorCode = errorObject.getString("code");
                                    String errorMessage = errorObject.getString("message");

                                    // Manejar la respuesta con código de respuesta 406 o 409 aquí
                                    // Por ejemplo, puedes mostrar el código y mensaje de error en la interfaz de usuario
                                    Log.e("Respuesta", "Éxito: " + success);
                                    Log.e("Respuesta", "Código de error: " + errorCode);
                                    Log.e("Respuesta", "Mensaje de error: " + errorMessage);

                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            case 502:
                                try {
                                    boolean success = response.getBoolean("success");
                                    JSONObject errorObject = response.getJSONObject("error");
                                    String errorCode = errorObject.getString("code");
                                    String errorMessage = errorObject.getString("message");

                                    // Manejar la respuesta con código de respuesta 502 aquí
                                    // Por ejemplo, puedes mostrar un mensaje específico de "Internal Server Error"
                                    Log.e("Respuesta", "Éxito: " + success);
                                    Log.e("Respuesta", "Código de error: " + errorCode);
                                    Log.e("Respuesta", "Mensaje de error: " + "Internal Server Error");

                                } catch (JSONException e) {
                                    Log.e("Error", "Error al procesar la respuesta JSON");
                                }
                                break;
                            default:
                                Log.e("Respuesta", "Código de respuesta desconocido: " + statusCode);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de solicitud aquí
                    }
                });

    // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

















