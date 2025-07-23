package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.NormasAdapter;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NormasdetFragment extends Fragment {

    private TextInputEditText et_idnorma, et_numnorma, et_descripcion;
    private Button btn_guardar;
    private ListView lv_normas;

    private ArrayList<NormasDeT> listaNormas;
    private NormasAdapter adapter;
    private NormasDeT normaSeleccionada;

    public NormasdetFragment() {}

    public static NormasdetFragment newInstance() {
        return new NormasdetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normasdet, container, false);

        et_idnorma = view.findViewById(R.id.et_idnorma);
        et_numnorma = view.findViewById(R.id.et_numnorma);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_normas = view.findViewById(R.id.lv_normas);

        listaNormas = new ArrayList<>();
        adapter = new NormasAdapter(requireContext(), listaNormas);
        lv_normas.setAdapter(adapter);

        cargarNormas();

        btn_guardar.setOnClickListener(v -> {
            String numNorma = et_numnorma.getText().toString();
            String descripcion = et_descripcion.getText().toString();

            if (numNorma.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            NormasDeT nuevaNorma = new NormasDeT(numNorma, descripcion);

            if (normaSeleccionada == null) {
                SupabaseClient.insertNorma(nuevaNorma, responseCallback("guardada"));
            } else {
                SupabaseClient.updateNorma(normaSeleccionada.getIdNorma(), nuevaNorma, responseCallback("actualizada"));
            }
        });

        lv_normas.setOnItemClickListener((parent, view1, position, id) -> {
            normaSeleccionada = listaNormas.get(position);
            et_idnorma.setText(String.valueOf(normaSeleccionada.getIdNorma()));
            et_numnorma.setText(normaSeleccionada.getNumNorma());
            et_descripcion.setText(normaSeleccionada.getDescripcion());
        });

        lv_normas.setOnItemLongClickListener((parent, view12, position, id) -> {
            NormasDeT norma = listaNormas.get(position);

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Eliminar norma: \"" + norma.getNumNorma() + "\"?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SupabaseClient.deleteNorma(norma.getIdNorma(), responseCallback("eliminada"));
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });

        return view;
    }

    private void cargarNormas() {
        SupabaseClient.getNormas(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al cargar normas", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    listaNormas.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        NormasDeT norma = new NormasDeT(
                                obj.getInt("id_norma"),
                                obj.getString("numnorma"),
                                obj.getString("descripcion")
                        );
                        listaNormas.add(norma);
                    }
                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                } catch (JSONException e) {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Error al parsear normas", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }

    private Callback responseCallback(String accion) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al " + accion, Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Norma " + accion, Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarNormas();
                });
            }
        };
    }

    private void limpiarCampos() {
        et_idnorma.setText("");
        et_numnorma.setText("");
        et_descripcion.setText("");
        normaSeleccionada = null;
    }
}
