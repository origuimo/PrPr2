package com.androidpprog2.regals;

import static com.androidpprog2.regals.EditGift.PICK_IMAGE_REQUEST;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registre extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre);

        ImageView foto = findViewById(R.id.profileImageView);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button registreButton = findViewById(R.id.botoregister);
        registreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nomUser = findViewById(R.id.Nom);
                String name = nomUser.getText().toString();
                EditText lastName = findViewById(R.id.Mail);
                String lastname = lastName.getText().toString();
                EditText mail = findViewById(R.id.Contrasenya);
                String email = mail.getText().toString();
                EditText passwordUser = findViewById(R.id.Contrasenya2);
                String password = passwordUser.getText().toString();
                ImageView foto = findViewById(R.id.profileImageView);
                makePost(name, lastname, email, password, foto);//pasali foto ?
                Intent intent = new Intent(Registre.this, LogIn.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.registre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado virtual
                hideKeyboard();
            }
        });
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            ImageView foto = findViewById(R.id.profileImageView);
            Picasso.get().load(imageUri).into(foto);
        }
    }

    private String saveBitmapToFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_image.jpg");

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = getCurrentFocus();
        if (currentFocusView != null) {
            imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
        }
    }

    public void makePost(String name, String lastname, String email, String password, ImageView foto) {
        JSONObject credentials = new JSONObject();
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        String imageUrl = saveBitmapToFile(bitmap);


        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users", credentials,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        int statusCode = response.optInt("code", -1); // Obtener el código de respuesta desde la respuesta JSON
                        switch (statusCode) {
                            case 200:
                                try {
                                    credentials.put("name", name);
                                    credentials.put("last_name", lastname);
                                    credentials.put("email", email);
                                    credentials.put("password", password);
                                    credentials.put("image", imageUrl);
                                } catch (Exception e) {
                                    Log.e("Error", "Hi ha hagut un error afegint els valors al JSON object ");
                                }
                                break;
                            case 400:
                                Log.e("Error", "Request incorrecta");
                                String message = "No es pot crear aquest usuari";
                                Toast.makeText(Registre.this, message, Toast.LENGTH_SHORT).show();
                                break;
                            case 406:
                                Log.e("Error", "Missing Parameters");
                                String message2 = "Omple tots els camps!";
                                Toast.makeText(Registre.this, message2, Toast.LENGTH_SHORT).show();
                                break;
                            case 409:
                                Log.e("Error", "Mail ja esta registrat");
                                String message3 = "Aquesta adreça de correu electronica ja està registrada!!";
                                Toast.makeText(Registre.this, message3, Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Log.e("Error", "No s'ha pogut crear l'usuari!");
                                break;
                            case 502:
                                Log.e("Error", "Internet server error!");
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Manejar el error de respuesta aquí
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jor); // Agrega la solicitud a la cola de solicitudes
    }
}
