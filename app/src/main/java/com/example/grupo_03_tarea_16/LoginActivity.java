package com.example.grupo_03_tarea_16;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etPassword;
    private Button btnLogin;

    private static final String BASE_URL = "https://wqpmwrgkkgfzdpsiyxhq.supabase.co/rest/v1";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndxcG13cmdra2dmemRwc2l5eGhxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMwMjM5OTYsImV4cCI6MjA2ODU5OTk5Nn0.WAXHiheRYrutcun7hcXlAJ8JGk2wacvqZnyjkfeqPKo";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!correo.isEmpty() && !password.isEmpty()) {
                verificarLogin(correo, password);
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarLogin(String correo, String password) {
        Uri uri = Uri.parse(BASE_URL + "/usuario")
                .buildUpon()
                .appendQueryParameter("correo", "eq." + correo)
                .appendQueryParameter("password", "eq." + password)
                .build();

        Request request = new Request.Builder()
                .url(uri.toString())
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(LoginActivity.this, "Error de red: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                runOnUiThread(() -> {
                    try {
                        if (!response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Error HTTP: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray array = new JSONArray(body);
                        if (array.length() > 0) {
                            JSONObject obj = array.getJSONObject(0);
                            String nombre = obj.getString("nombres");
                            Toast.makeText(LoginActivity.this, "Bienvenido, " + nombre, Toast.LENGTH_LONG).show();

                            // Redirigir a MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Error al procesar respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
