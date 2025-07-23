package com.example.grupo_03_tarea_16.apartadomenu;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.PropietarioAdapter;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PropietarioFragment extends Fragment {

    private TextInputEditText etCedula, etNombre, etCiudad;
    private Button btnGuardar;
    private ListView lvPropietarios;
    private PropietarioAdapter adapter;
    private ArrayList<Propietario> listaPropietarios = new ArrayList<>();
    private boolean isEditing = false;
    private String cedulaEditando = null;

    public PropietarioFragment() {}

    public static PropietarioFragment newInstance() {
        return new PropietarioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_propietario, container, false);

        etCedula = view.findViewById(R.id.et_cedula);
        etNombre = view.findViewById(R.id.et_nombre);
        etCiudad = view.findViewById(R.id.et_ciudad);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvPropietarios = view.findViewById(R.id.lv_propietarios);

        adapter = new PropietarioAdapter(requireContext(), listaPropietarios);
        lvPropietarios.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> {
            String cedula = etCedula.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String ciudad = etCiudad.getText().toString().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Propietario propietario = new Propietario(cedula, nombre, ciudad);

            if (isEditing) {
                // CORREGIDO: cedulaEditando como primer parámetro
                SupabaseClient.updatePropietario(cedulaEditando, propietario, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            cargarPropietarios();
                        });
                    }
                });
            } else {
                SupabaseClient.insertPropietario(propietario, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(requireContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                                cargarPropietarios();
                            });
                        } else {
                            Log.e("Supabase", "Error: " + response.body().string());
                        }
                    }
                });
            }
        });


        lvPropietarios.setOnItemClickListener((parent, view1, position, id) -> {
            Propietario seleccionado = listaPropietarios.get(position);
            etCedula.setText(seleccionado.getCedulaP());
            etCedula.setEnabled(false); // No editable la cédula
            etNombre.setText(seleccionado.getNombre());
            etCiudad.setText(seleccionado.getCiudad());
            isEditing = true;
            cedulaEditando = seleccionado.getCedulaP();
            btnGuardar.setText("Actualizar");
        });

        lvPropietarios.setOnItemLongClickListener((parent, view12, position, id) -> {
            Propietario seleccionado = listaPropietarios.get(position);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar")
                    .setMessage("¿Deseas eliminar al propietario con cédula " + seleccionado.getCedulaP() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SupabaseClient.deletePropietario(seleccionado.getCedulaP(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                requireActivity().runOnUiThread(() ->
                                        Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    cargarPropietarios();
                                });
                            }
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        cargarPropietarios();
        return view;
    }

    private void cargarPropietarios() {
        SupabaseClient.getDataFromTable("propietario", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        listaPropietarios.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Propietario p = new Propietario(
                                    obj.getString("cedulap"),
                                    obj.getString("nombre"),
                                    obj.getString("ciudad")
                            );
                            listaPropietarios.add(p);
                        }
                        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void limpiarCampos() {
        etCedula.setText("");
        etCedula.setEnabled(true);
        etNombre.setText("");
        etCiudad.setText("");
        btnGuardar.setText("Guardar");
        isEditing = false;
        cedulaEditando = null;
    }
}
