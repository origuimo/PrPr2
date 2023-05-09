package com.androidpprog2.regals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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


        Button btnRegistre = findViewById(R.id.botoregister);
        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, GenerateActivity.class);
                try {
                    intent.putExtra("name", userObject.getString("name"));
                    intent.putExtra("lastname", userObject.getString("lastname"));
                    intent.putExtra("email", userObject.getString("email"));
                    intent.putExtra("password", userObject.getString("password"));
                    intent.putExtra("image", userObject.getString("image"));

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                context.startActivity(intent);
            }

        });

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        try {
            Picasso.get().load(imageUrl).into(viewHolder.getImageView());
            //D'on / com faig els gets de la resta de variables
            viewHolder.bind(localDataSet.getJSONObject(position));
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

    }
    public void makePost() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api-docs/v1/#/Users/post_users", new Response.Listener<String>() {
            @Override
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
                params.put("username", "");
                params.put("password", "");
                params.put("email", "");
                params.put("lastName", "");
                params.put("template_id", Integer.toString(userId));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

}
