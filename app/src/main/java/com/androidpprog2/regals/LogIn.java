package com.androidpprog2.regals;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogIn extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login = (Button) findViewById(R.id.btnLogIn);
        EditText email = (EditText) findViewById(R.id.Mail);
        EditText contra = (EditText) findViewById(R.id.Contrasenya);
        TextView signup = (TextView) findViewById(R.id.textsignup);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String contrasenya = contra.getText().toString();
                Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
                Matcher matcher = pattern.matcher(contrasenya);

                if (!mail.contains("@") || !matcher.matches()) {
                    Snackbar.make(findViewById(R.id.loginlayout), R.string.error1,
                                    Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    try {
                        URL url = new URL("https://balandrau.salle.url.edu/dpoo/shared/monsters");
                        //URL url = new URL("https://monet.cat/posts/fromfeed2");

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setConnectTimeout(10000);
                        connection.setReadTimeout(10000);

                        //connection.setRequestProperty("api_key", "YOUR_API_KEY");
                        connection.connect();

                        InputStream response = connection.getInputStream();
                        String resposta = new BufferedReader(new InputStreamReader(response)).lines().collect(Collectors.joining());
                        // Procesar la respuesta aquí
                        System.out.println(resposta);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Couldn’t connect to the remote server.\nReverting to local data.\n");
                    }
                }
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
}
