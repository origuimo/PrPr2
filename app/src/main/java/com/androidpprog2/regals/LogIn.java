package com.androidpprog2.regals;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class LogIn extends AppCompatActivity {
    String token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login = (Button) findViewById(R.id.btnLogIn);
        EditText email = (EditText) findViewById(R.id.Mail);
        EditText contra = (EditText) findViewById(R.id.Contrasenya);
        contra.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        TextView signup = (TextView) findViewById(R.id.textsignup);

        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

               String mail = email.getText().toString();
                String contrasenya = contra.getText().toString();
                Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
                Matcher matcher = pattern.matcher(contrasenya);
                if (!mail.contains("@") || !matcher.matches()) {
                    Snackbar.make(findViewById(R.id.loginlayout), R.string.error1, Snackbar.LENGTH_SHORT).show();
                } else {
                    postUsersLogin(mail, contrasenya);
                    Intent intent = new Intent(LogIn.this, Feed.class);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.loginlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado virtual
                hideKeyboard();
            }
        });

        signup.setMovementMethod(LinkMovementMethod.getInstance());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Registre.class);
                startActivity(intent);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = getCurrentFocus();
        if (currentFocusView != null) {
            imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
        }
    }

    public void postUsersLogin(String email, String password) {

       JSONObject   credentials = new JSONObject();

       try {
           credentials.put("email", email);
           credentials.put("password",password);
       }catch (Exception e ){
           Log.e("Error","Hi ha hagut un error afegint els valors al JSON object ");
       }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://balandrau.salle.url.edu/i3/socialgift/api/v1/users/login", credentials,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                /*
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("accessToken")) {
                        String accessToken = jsonResponse.getString("accessToken");
                        Intent intent = new Intent(LogIn.this, Feed.class);
                        startActivity(intent);
                    } else if (jsonResponse.has("error")) {
                        JSONObject error = jsonResponse.getJSONObject("error");
                        String errorCode = error.getString("code");
                        String errorMessage = error.getString("message");
                        Snackbar.make(findViewById(R.id.loginlayout), errorMessage, Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                error.getMessage();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", password);
                params.put("email", email);
                return params;
            }


        };
        queue.add(jor);
    }
}
