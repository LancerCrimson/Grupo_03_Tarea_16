package com.example.grupo_03_tarea_16.fragmentos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.AccidenteAdapter;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AccidenteFragment extends Fragment {

    private static final int REQ_CAMARA = 101;
    private Spinner spPlaca, spAgente;
    private TextInputEditText etFecha, etHora, etDescripcion;
    private Button btnFoto, btnUbicacion, btnGuardar;
    private ListView lvAccidentes;

    private byte[] fotoBytes = null;
    private double latitud = 0.0;
    private double longitud = 0.0;

    private List<Accidente> listaAccidentes = new ArrayList<>();
    private List<String> placas = new ArrayList<>();
    private List<Agente> agentes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accidente, container, false);

        spPlaca = view.findViewById(R.id.sp_placa);
        spAgente = view.findViewById(R.id.sp_agente);
        etFecha = view.findViewById(R.id.et_fecha);
        etHora = view.findViewById(R.id.et_hora);
        etDescripcion = view.findViewById(R.id.et_descripcion);
        btnFoto = view.findViewById(R.id.btn_foto_accidente);
        btnUbicacion = view.findViewById(R.id.btn_ubicacion);
        btnGuardar = view.findViewById(R.id.btn_guardar_accidente);
        lvAccidentes = view.findViewById(R.id.lv_accidentes);

        cargarSpinners();
        cargarAccidentes();

        etFecha.setOnClickListener(v -> mostrarDatePicker());
        etHora.setOnClickListener(v -> mostrarTimePicker());
        btnFoto.setOnClickListener(v -> tomarFoto());
        btnUbicacion.setOnClickListener(v -> simularUbicacion());

        btnGuardar.setOnClickListener(v -> guardarAccidente());

        return view;
    }

    private void cargarSpinners() {
        SupabaseClient.getVehiculos(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    placas.clear();
                    for (int i = 0; i < array.length(); i++) {
                        placas.add(array.getJSONObject(i).getString("numplaca"));
                    }
                    requireActivity().runOnUiThread(() -> spPlaca.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, placas)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });

        SupabaseClient.listarAgentes(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    agentes.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Agente agente = new Agente(obj.getInt("id_agente"), obj.getString("nombre"));
                        agentes.add(agente);
                        nombres.add(agente.getNombre());
                    }
                    requireActivity().runOnUiThread(() -> spAgente.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });
    }

    private void cargarAccidentes() {
        SupabaseClient.listarAccidentes(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaAccidentes.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        Accidente acc = new Accidente(
                                o.getInt("id_accidente"),
                                o.getString("numplaca"),
                                o.getInt("id_agente"),
                                o.getString("hora"),
                                o.getString("fecha"),
                                o.getString("descripcion"),
                                o.getDouble("latitud"),
                                o.getDouble("longitud"),
                                o.has("media") && !o.isNull("media") ? android.util.Base64.decode(o.getString("media"), android.util.Base64.NO_WRAP) : null
                        );
                        listaAccidentes.add(acc);
                    }
                    requireActivity().runOnUiThread(() -> lvAccidentes.setAdapter(new AccidenteAdapter(requireContext(), listaAccidentes)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });
    }

    private void mostrarDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, month, day) -> etFecha.setText(year + "-" + (month + 1) + "-" + day),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void mostrarTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(requireContext(), (view, hour, minute) -> etHora.setText(String.format("%02d:%02d", hour, minute)),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQ_CAMARA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                simularUbicacion(); // Llama de nuevo ahora que tienes permiso
            } else {
                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void simularUbicacion() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();
                            Toast.makeText(requireContext(), "Ubicación obtenida", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        }
    }


    private void guardarAccidente() {
        String placa = spPlaca.getSelectedItem().toString();
        int idAgente = agentes.get(spAgente.getSelectedItemPosition()).getIdAgente();
        String hora = etHora.getText().toString();
        String fecha = etFecha.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if (placa.isEmpty() || hora.isEmpty() || fecha.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Accidente accidente = new Accidente(placa, idAgente, hora, fecha, descripcion, latitud, longitud, fotoBytes);
        SupabaseClient.insertarAccidente(accidente, new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Accidente registrado", Toast.LENGTH_SHORT).show();
                    cargarAccidentes();
                });
            }
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CAMARA && data != null) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
            fotoBytes = stream.toByteArray();
            Toast.makeText(requireContext(), "Foto capturada", Toast.LENGTH_SHORT).show();
        }
    }
}
