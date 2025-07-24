package com.example.grupo_03_tarea_16.apartadomenu;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.ZonaAdapter;
import com.example.grupo_03_tarea_16.modelo.Zona;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ZonaFragment extends Fragment {

    private TextInputEditText etIdZona, etUbicacion;
    private Button btnGuardar;
    private ListView lvZonas;
    private ArrayList<Zona> listaZonas = new ArrayList<>();
    private ZonaAdapter zonaAdapter;

    private boolean isEditing = false;
    private int idZonaEditando = -1;

    public ZonaFragment() {}

    public static ZonaFragment newInstance() {
        return new ZonaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zona, container, false);

        etIdZona = view.findViewById(R.id.et_idzona);
        etUbicacion = view.findViewById(R.id.et_ubicacion);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvZonas = view.findViewById(R.id.lv_zona);

        zonaAdapter = new ZonaAdapter(requireContext(), listaZonas);
        lvZonas.setAdapter(zonaAdapter);

        btnGuardar.setOnClickListener(v -> {
            String ubicacion = etUbicacion.getText().toString().trim();
            if (ubicacion.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese ubicación", Toast.LENGTH_SHORT).show();
                return;
            }

            Zona zona = new Zona();
            zona.setUbicacion(ubicacion);

            if (isEditing) {
                SupabaseClient.updateZona(idZonaEditando, zona, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Zona actualizada", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            cargarZonas();
                        });
                    }
                });
            } else {
                SupabaseClient.insertZona(zona, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "Error al insertar", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Zona guardada", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            cargarZonas();
                        });
                    }
                });
            }
        });

        lvZonas.setOnItemClickListener((parent, view1, position, id) -> {
            Zona zona = listaZonas.get(position);
            etIdZona.setText(String.valueOf(zona.getIdZona()));
            etIdZona.setEnabled(false);
            etUbicacion.setText(zona.getUbicacion());

            isEditing = true;
            idZonaEditando = zona.getIdZona();
            btnGuardar.setText("Actualizar");
        });

        lvZonas.setOnItemLongClickListener((parent, view12, position, id) -> {
            Zona zona = listaZonas.get(position);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar zona")
                    .setMessage("¿Deseas eliminar la zona con ID " + zona.getIdZona() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SupabaseClient.deleteZona(zona.getIdZona(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                requireActivity().runOnUiThread(() ->
                                        Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                                requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Zona eliminada", Toast.LENGTH_SHORT).show();
                                    cargarZonas();
                                });
                            }
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        cargarZonas();
        return view;
    }

    private void cargarZonas() {
        SupabaseClient.getZonas(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al cargar zonas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String data = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        listaZonas.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Zona zona = new Zona();
                            zona.setIdZona(obj.getInt("id_zona"));
                            zona.setUbicacion(obj.getString("ubicacion"));
                            listaZonas.add(zona);
                        }
                        requireActivity().runOnUiThread(() -> zonaAdapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void limpiarCampos() {
        etIdZona.setText("");
        etIdZona.setEnabled(true);
        etUbicacion.setText("");
        isEditing = false;
        idZonaEditando = -1;
        btnGuardar.setText("Guardar");
    }
}
