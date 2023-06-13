package com.androidpprog2.regals;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EditGift extends AppCompatActivity {
    private Uri imageUri;
    static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_gift);
        Button btnSave = findViewById(R.id.button3);
        EditText nom = (EditText) findViewById(R.id.editText6);
        EditText descripcio = (EditText) findViewById(R.id.editText7);
        EditText link = (EditText) findViewById(R.id.editText9);
        EditText price = (EditText) findViewById(R.id.editText8);
        EditText category = (EditText) findViewById(R.id.editText10);
        EditText ID = (EditText) findViewById(R.id.editText12);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noms = nom.getText().toString();
                String descripcion = descripcio.getText().toString();
                String enllaç = link.getText().toString();
                String preu = price.getText().toString();
                String categoria = category.getText().toString();
                String Id = ID.getText().toString();

                putGift(noms, descripcion, enllaç, preu, categoria, Id);

                Intent intent = new Intent(getApplicationContext(), GiftListActivity.class); //Si apreten el boto save torna a la pantalla dels regals , o millor hauria de tornar al feed ?
                startActivity(intent);
            }
        });

        Button btnImage = findViewById(R.id.button5);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Si apreten el boto save torna a la pantalla dels regals , o millor hauria de tornar al feed ?
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }
    //Potser hem de donar permis al manifest per accedir a la galeria de imatges per pujar la foto del regal
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(imageUri);
        }
    }

    public void putGift(String nom, String descripcio, String link, String price, String category, String Id){
        JSONObject updatedData = new JSONObject();

        try {
            updatedData.put("name", nom);
            updatedData.put("description", descripcio);
            updatedData.put("link", link);
            updatedData.put("photo", link);
            updatedData.put("price", price);
            updatedData.put("categoryIds", category);
        } catch (Exception e) {
            Log.e("Error", "Hi ha hagut un error afegint els valors al JSON object ");
        }

        int idProducto = Integer.parseInt(Id);

        String url = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/" + idProducto;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, updatedData,
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
                            case 200:
                                try {
                                    int id = response.getInt("id");
                                    String name = response.getString("name");
                                    String description = response.getString("description");
                                    String link = response.getString("link");
                                    String photo = response.getString("photo");
                                    double price = response.getDouble("price");
                                    int categoryIds = response.getInt("categoryIds");

                                    // Manejar la respuesta con código de respuesta 200 aquí
                                    // Por ejemplo, puedes mostrar los datos recibidos en la interfaz de usuario
                                    Log.d("Respuesta", "ID: " + id);
                                    Log.d("Respuesta", "Nombre: " + name);
                                    Log.d("Respuesta", "Descripción: " + description);
                                    Log.d("Respuesta", "Enlace: " + link);
                                    Log.d("Respuesta", "Foto: " + photo);
                                    Log.d("Respuesta", "Precio: " + price);
                                    Log.d("Respuesta", "ID de categoría: " + categoryIds);

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
                            case 401:
                                Log.e("Error", "No autorizado");
                                break;
                            case 406:
                            case 409:
                            case 410:
                                try {
                                    boolean success = response.getBoolean("success");
                                    JSONObject error = response.getJSONObject("error");
                                    String errorCode = error.getString("code");
                                    String errorMessage = error.getString("message");

                                    Log.e("Respuesta", "Éxito: " + success);
                                    Log.e("Respuesta", "Código de error: " + errorCode);
                                    Log.e("Respuesta", "Mensaje de error: " + errorMessage);

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
                        // Manejar errores de solicitud aquí
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}