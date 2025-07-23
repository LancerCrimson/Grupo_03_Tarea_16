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
import com.example.grupo_03_tarea_16.adapter.adaptermenu.AudienciaAdapter;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AudienciaFragment extends Fragment {

    private TextInputEditText etIdAudiencia, etLugar, etFecha, etHora;
    private ListView lvAudiencia;
    private Button btnGuardar;
    private ArrayList<Audiencia> listaAudiencia;
    private AudienciaAdapter adapter;
    private Audiencia audienciaSeleccionada;

    public AudienciaFragment() {}

    public static AudienciaFragment newInstance() {
        return new AudienciaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audiencia, container, false);

        etIdAudiencia = view.findViewById(R.id.et_idaudiencia);
        etLugar = view.findViewById(R.id.et_lugar);
        etFecha = view.findViewById(R.id.et_fecha);
        etHora = view.findViewById(R.id.et_hora);
        lvAudiencia = view.findViewById(R.id.lv_audiencia);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        listaAudiencia = new ArrayList<>();
        adapter = new AudienciaAdapter(requireContext(), listaAudiencia);
        lvAudiencia.setAdapter(adapter);

        cargarAudiencias();

        btnGuardar.setOnClickListener(v -> {
            String lugar = etLugar.getText().toString();
            String fecha = etFecha.getText().toString();
            String hora = etHora.getText().toString();

            if (lugar.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Audiencia nuevaAudiencia = new Audiencia(lugar, fecha, hora);

            if (audienciaSeleccionada == null) {
                // INSERTAR
                SupabaseClient.insertAudiencia(nuevaAudiencia, responseCallback("guardada"));
            } else {
                // ACTUALIZAR
                SupabaseClient.updateAudiencia(audienciaSeleccionada.getIdAudiencia(), nuevaAudiencia, responseCallback("actualizada"));
            }
        });

        // EDITAR
        lvAudiencia.setOnItemClickListener((parent, view1, position, id) -> {
            audienciaSeleccionada = listaAudiencia.get(position);
            etIdAudiencia.setText(String.valueOf(audienciaSeleccionada.getIdAudiencia()));
            etLugar.setText(audienciaSeleccionada.getLugar());
            etFecha.setText(audienciaSeleccionada.getFecha());
            etHora.setText(audienciaSeleccionada.getHora());
        });

        lvAudiencia.setOnItemLongClickListener((parent, view12, position, id) -> {
            Audiencia audiencia = listaAudiencia.get(position);

            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar esta audiencia?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        SupabaseClient.deleteAudiencia(audiencia.getIdAudiencia(), responseCallback("eliminada"));
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });


        return view;
    }

    private void cargarAudiencias() {
        SupabaseClient.getAudiencias(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    listaAudiencia.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Audiencia audiencia = new Audiencia(
                                obj.getInt("id_audiencia"),
                                obj.getString("lugar"),
                                obj.getString("fecha"),
                                obj.getString("hora")
                        );
                        listaAudiencia.add(audiencia);
                    }
                    requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                } catch (JSONException e) {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(requireContext(), "Error al parsear datos", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(requireContext(), "Audiencia " + accion, Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarAudiencias();
                });
            }
        };
    }

    private void limpiarCampos() {
        etIdAudiencia.setText("");
        etLugar.setText("");
        etFecha.setText("");
        etHora.setText("");
        audienciaSeleccionada = null;
    }
}
