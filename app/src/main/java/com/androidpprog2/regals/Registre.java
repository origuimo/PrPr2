package com.androidpprog2.regals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
                EditText emailUser = findViewById(R.id.Mail);
                String email = emailUser.getText().toString();
                EditText passwordUser = findViewById(R.id.Contrasenya);
                String password = passwordUser.getText().toString();
                //ImageView foto = findViewById(R.id.)
                makePost(nom,email,password);//pasali foto ?


            }
        });

    }

    public void makePost(String nom, String email , String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api-docs/v1/#/Users/post_users", new Response.Listener<String>() {
            @Override //El que vull que pasi un cop tinc ña resposta xomprovo si m'ha tornat ok o no
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", nom);
                params.put("password", password);
                params.put("email", email);
               // params.put("lastName", "");
               // params.put("template_id", Integer.toString());
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
