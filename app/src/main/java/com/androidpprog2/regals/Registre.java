package com.androidpprog2.regals;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

        Button registreButton = (Button) findViewById(R.id.botoregister);
        registreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                setContentView(R.layout.registre);
                EditText nomUser = findViewById(R.id.Nom);
                String nom = nomUser.getText().toString();
                EditText lastName = findViewById(R.id.Mail);
                String nom2 = lastName.getText().toString();
                EditText mail = findViewById(R.id.Contrasenya);
                String email = mail.getText().toString();
                EditText passwordUser = findViewById(R.id.Contrasenya2);
                String password = passwordUser.getText().toString();
                ImageView foto = findViewById(R.id.profileImageView);
                makePost(nom,nom2,email,password,foto);//pasali foto ?


            }
        });
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
    public void makePost(String nom,String nom2, String email , String password, ImageView foto) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        String imageUrl = saveBitmapToFile(bitmap);

        StringRequest sr = new StringRequest(Request.Method.POST,"https://balandrau.salle.url.edu/i3/socialgift/api/v1/users", new Response.Listener<String>() {
            @Override //El que vull que pasi un cop tinc la resposta comprovo si m'ha tornat ok o no
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mira si el usuario ya existe mas printalo por pantalla?
            }
        }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", nom);
                params.put("lastname", nom2);
                params.put("email", email);
                params.put("password", password);
                foto.setDrawingCacheEnabled(true);
                foto.buildDrawingCache();
                Bitmap bitmap = foto.getDrawingCache();
                String imageUrl = saveBitmapToFile(bitmap);
                if (imageUrl != null) {
                    params.put("image", imageUrl);
                }
                return params;
            }

            @Override //Al la resta ficare header d'autenticacio que agafo del log in
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

}
