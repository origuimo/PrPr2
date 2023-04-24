package com.androidpprog2.regals;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ApiRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String url;
    private final Map<String, String> headers;
    private final Response.Listener<JSONObject> listener;
    private final Response.ErrorListener errorListener;

    public ApiRequest(String url, Map<String, String> headers, Response.Listener<JSONObject> listener,
                      Response.ErrorListener errorListener) {
        this.url = url;
        this.headers = headers;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public void sendRequest(Context context) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public void onResponse(JSONObject response) {
        /*try {
            // Obtenemos el token de acceso del objeto JSON recibido
            String token = response.getString("accessToken");

            // Guardamos el token en SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences("mi_app", MODE_PRIVATE).edit();
            editor.putString("accessToken", token);
            editor.apply();

            // Notificamos al listener que el inicio de sesión fue exitoso
            listener.onLoginSuccess();
        } catch (JSONException e) {
            // Manejamos el error de JSON
            listener.onLoginError("Error al procesar la respuesta del servidor.");
        }
        */
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // Manejar los errores de la API aquí
    }
}
