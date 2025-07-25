package com.example.grupo_03_tarea_16.apartadomenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.VehiculoAdapter;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VehiculoFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1002;

    private TextInputEditText etPlaca, etMarca, etModelo, etMotor, etYear;
    private Spinner spCedula;
    private Button btnFoto, btnGuardar;
    private ListView lvVehiculos;
    private ImageView imgPreview;

    private List<Propietario> listaPropietarios = new ArrayList<>();
    private byte[] imagenSeleccionada = null;

    public VehiculoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehiculo, container, false);

        etPlaca = view.findViewById(R.id.et_numplaca);
        etMarca = view.findViewById(R.id.et_marca);
        etModelo = view.findViewById(R.id.et_modelo);
        etMotor = view.findViewById(R.id.et_motor);
        etYear = view.findViewById(R.id.et_year);
        spCedula = view.findViewById(R.id.sp_cedulap);
        btnFoto = view.findViewById(R.id.btn_seleccionar_foto);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvVehiculos = view.findViewById(R.id.lv_vehiculos);

        // 🔧 Esta imagen la vas a inflar con el ImageView que le asignaremos por código
        imgPreview = new ImageView(requireContext());
        imgPreview.setLayoutParams(new ViewGroup.LayoutParams(300, 300)); // Tamaño deseado opcional
        ((ViewGroup) view).addView(imgPreview); // Añadirlo al layout programáticamente

        cargarPropietarios();
        cargarVehiculos();

        btnFoto.setOnClickListener(v -> tomarFoto());
        btnGuardar.setOnClickListener(v -> guardarVehiculo());

        return view;
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getContext(), "No se puede abrir la cámara", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                imagenSeleccionada = stream.toByteArray();

                // Mostrar imagen tomada
                imgPreview.setImageBitmap(bitmap);
                Toast.makeText(getContext(), "Foto tomada correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarVehiculo() {
        String placa = etPlaca.getText().toString();
        String marca = etMarca.getText().toString();
        String modelo = etModelo.getText().toString();
        String motor = etMotor.getText().toString();
        String yearStr = etYear.getText().toString();
        String cedula = spCedula.getSelectedItem().toString();

        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || motor.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);

        Vehiculo v = new Vehiculo(placa, marca, modelo, motor, year, imagenSeleccionada, cedula);

        SupabaseClient.insertVehiculo(v, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Vehículo guardado", Toast.LENGTH_SHORT).show();
                    cargarVehiculos();
                });
            }
        });
    }

    private void cargarPropietarios() {
        SupabaseClient.getPropietarios(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error al cargar propietarios", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray json = new JSONArray(response.body().string());
                    listaPropietarios.clear();
                    List<String> cedulas = new ArrayList<>();

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject obj = json.getJSONObject(i);
                        Propietario p = new Propietario(
                                obj.getString("cedulap"),
                                obj.getString("nombre"),
                                obj.getString("ciudad")
                        );
                        listaPropietarios.add(p);
                        cedulas.add(p.getCedulaP());
                    }

                    requireActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cedulas);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spCedula.setAdapter(adapter);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cargarVehiculos() {
        SupabaseClient.getVehiculos(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error al cargar vehículos", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray json = new JSONArray(response.body().string());
                    List<Vehiculo> vehiculos = new ArrayList<>();

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject obj = json.getJSONObject(i);

                        String base64Media = obj.optString("media", "");
                        byte[] mediaBytes = null;
                        if (!base64Media.isEmpty() && !base64Media.equals("null")) {
                            mediaBytes = android.util.Base64.decode(base64Media, android.util.Base64.DEFAULT);
                        }

                        Vehiculo v = new Vehiculo(
                                obj.getString("numplaca"),
                                obj.getString("marca"),
                                obj.getString("modelo"),
                                obj.getString("motor"),
                                obj.getInt("year"),
                                mediaBytes,
                                obj.getString("cedulap")
                        );
                        vehiculos.add(v);
                    }

                    requireActivity().runOnUiThread(() -> {
                        VehiculoAdapter adapter = new VehiculoAdapter(getContext(), vehiculos);
                        lvVehiculos.setAdapter(adapter);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
