package com.androidpprog2.regals;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class LogIn extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView login = (TextView) findViewById(R.id.btnLogIn);
        EditText email = (EditText) findViewById(R.id.Mail);
        EditText contra = (EditText) findViewById(R.id.Contrasenya);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String contrasenya = contra.getText().toString();

                if (!mail.contains("@")) {
                    Snackbar.make(findViewById(R.id.loginlayout), R.string.error1,
                                    Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    //Intent intent = new Intent(LogIn.this, VisualizationEvents.class);
                    //startActivity(intent);
                }
            }
        });
    }
}
