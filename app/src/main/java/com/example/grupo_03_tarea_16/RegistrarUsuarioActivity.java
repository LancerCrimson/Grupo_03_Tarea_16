package com.example.grupo_03_tarea_16;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo_03_tarea_16.SupabaseClient;

import com.example.grupo_03_tarea_16.modelo.Usuario;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword, etConfirmPassword;
    private Button btnRegistrar;
    private LinearLayout txtIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etCorreo = findViewById(R.id.et_correo);
        etPassword = findViewById(R.id.et_contraseña);
        etConfirmPassword = findViewById(R.id.et_confirmarcontraseña);
        btnRegistrar = findViewById(R.id.btnregistrar);
        txtIniciarSesion = findViewById(R.id.txt_iniciarsesion);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String apellido = etApellido.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }else if(password.equals(confirmPassword)){
                Usuario nuevo = new Usuario(0,nombre,apellido,correo,password);
                SupabaseClient.insertarUsuario(nuevo, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                    }
                });
                Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
            etNombre.setText("");
            etApellido.setText("");
            etCorreo.setText("");
            etPassword.setText("");
            etConfirmPassword.setText("");

        });

        txtIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ImageView circlePulse = findViewById(R.id.circulo);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circlePulse, "scaleX", 1f, 2f);
        scaleX.setDuration(1000);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circlePulse, "scaleY", 1f, 2f);
        scaleY.setDuration(1000);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(circlePulse, "alpha", 1f, 0f);
        fadeOut.setDuration(1000);
        fadeOut.setRepeatCount(ObjectAnimator.INFINITE);
        fadeOut.setRepeatMode(ObjectAnimator.RESTART);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, fadeOut);
        animatorSet.start();
    }
}