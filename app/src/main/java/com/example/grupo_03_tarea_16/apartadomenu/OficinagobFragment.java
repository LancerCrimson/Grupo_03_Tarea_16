package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.OficinaAdapter;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OficinagobFragment extends Fragment {

    private TextInputEditText etId, etValor, etPoliza, etUbicacion;
    private Spinner spnPlaca;
    private Button btnGuardar;
    private ListView lvOficina;

    private ArrayList<String> placasList = new ArrayList<>();
    private ArrayList<OficinaGob> oficinaList = new ArrayList<>();
    private OficinaAdapter oficinaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oficinagob, container, false);

        etId = view.findViewById(R.id.et_idoficinagob);
        etValor = view.findViewById(R.id.et_valorvehiculo);
        etPoliza = view.findViewById(R.id.et_npoliza);
        etUbicacion = view.findViewById(R.id.et_ubicacion);
        spnPlaca = view.findViewById(R.id.spn_numplaca);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvOficina = view.findViewById(R.id.lv_oficina);

        oficinaAdapter = new OficinaAdapter(requireContext(), oficinaList);
        lvOficina.setAdapter(oficinaAdapter);

        cargarPlacas();
        cargarOficinas();

        btnGuardar.setOnClickListener(v -> insertarOficina());

        return view;
    }

    private void cargarPlacas() {
        SupabaseClient.getVehiculos(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error cargando placas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        placasList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            placasList.add(obj.getString("numplaca"));
                        }
                        requireActivity().runOnUiThread(() -> {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, placasList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnPlaca.setAdapter(adapter);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void cargarOficinas() {
        SupabaseClient.getOficinaGobs(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al cargar oficinas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        oficinaList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            OficinaGob o = new OficinaGob(
                                    obj.getInt("id_oficinagob"),
                                    obj.getDouble("valorvehiculo"),
                                    obj.getString("npoliza"),
                                    obj.getString("numplaca"),
                                    obj.getString("ubicacion")
                            );
                            oficinaList.add(o);
                        }
                        requireActivity().runOnUiThread(() -> oficinaAdapter.notifyDataSetChanged());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void insertarOficina() {
        String valorStr = etValor.getText().toString();
        String npoliza = etPoliza.getText().toString();
        String ubicacion = etUbicacion.getText().toString();
        String placaSeleccionada = spnPlaca.getSelectedItem().toString();

        if (valorStr.isEmpty() || npoliza.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorVehiculo = Double.parseDouble(valorStr);
        OficinaGob nueva = new OficinaGob(valorVehiculo, npoliza, placaSeleccionada, ubicacion);

        SupabaseClient.insertOficinaGob(nueva, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al insertar", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(requireContext(), "Oficina insertada", Toast.LENGTH_SHORT).show();
                        cargarOficinas();
                    });
                }
            }
        });
    }
}
