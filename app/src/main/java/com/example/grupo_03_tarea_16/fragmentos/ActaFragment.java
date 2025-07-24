/*package com.example.grupo_03_tarea_16.fragmentos;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.ActaAdapter;
import com.example.grupo_03_tarea_16.modelo.*;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActaFragment extends Fragment {

    private Spinner spAccidente, spAudiencia, spZona, spAgente;
    private TextInputEditText etHora, etFecha;
    private Button btnGuardar;
    private ListView lvActas;

    private final List<Accidente> accidentes = new ArrayList<>();
    private final List<Audiencia> audiencias = new ArrayList<>();
    private final List<Zona> zonas = new ArrayList<>();
    private final List<Agente> agentes = new ArrayList<>();
    private final List<Acta> actas = new ArrayList<>();

    private ActaAdapter actaAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acta, container, false);

        // Inicializar vistas
        spAccidente = view.findViewById(R.id.spAccidente);
        spAudiencia = view.findViewById(R.id.spAudiencia);
        spZona = view.findViewById(R.id.spZona);
        spAgente = view.findViewById(R.id.spAgente);
        etHora = view.findViewById(R.id.etHora);
        etFecha = view.findViewById(R.id.etFecha);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        lvActas = view.findViewById(R.id.lvActas);

        actaAdapter = new ActaAdapter(requireContext(), actas);
        lvActas.setAdapter(actaAdapter);

        cargarSpinners();
        cargarActas();

        btnGuardar.setOnClickListener(v -> guardarActa());

        return view;
    }

    private void cargarSpinners() {
        SupabaseClient.listarAccidentes(new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    accidentes.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Accidente accidente = new Accidente(
                                obj.getInt("id_accidente"),
                                obj.getString("descripcion")
                        );
                        accidentes.add(accidente);
                        nombres.add(accidente.getDescripcion());
                    }
                    requireActivity().runOnUiThread(() ->
                            spAccidente.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });

        SupabaseClient.getAudiencias(new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    audiencias.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Audiencia audiencia = new Audiencia(
                                obj.getInt("id_audiencia"),
                                obj.getString("descripcion")
                        );
                        audiencias.add(audiencia);
                        nombres.add(audiencia.getDescripcion());
                    }
                    requireActivity().runOnUiThread(() ->
                            spAudiencia.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });

        SupabaseClient.getZonas(new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    zonas.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Zona zona = new Zona(
                                obj.getInt("id_zona"),
                                obj.getString("ubicacion")
                        );
                        zonas.add(zona);
                        nombres.add(zona.getNombre());  // Usa getUbicacion() si getNombre() no existe
                    }
                    requireActivity().runOnUiThread(() ->
                            spZona.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });

        SupabaseClient.listarAgentes(new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    agentes.clear();
                    List<String> nombres = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Agente agente = new Agente(
                                obj.getInt("id_agente"),
                                obj.getString("nombre")
                        );
                        agentes.add(agente);
                        nombres.add(agente.getNombre());
                    }
                    requireActivity().runOnUiThread(() ->
                            spAgente.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombres)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });
    }

    private void guardarActa() {
        String hora = etHora.getText().toString();
        String fecha = etFecha.getText().toString();

        if (hora.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idAccidente = accidentes.get(spAccidente.getSelectedItemPosition()).getIdAccidente();
        int idAudiencia = audiencias.get(spAudiencia.getSelectedItemPosition()).getIdAudiencia();
        int idZona = zonas.get(spZona.getSelectedItemPosition()).getIdZona();
        int idAgente = agentes.get(spAgente.getSelectedItemPosition()).getIdAgente();

        Acta acta = new Acta(idAccidente, idAudiencia, hora, idZona, idAgente, fecha);

        SupabaseClient.insertarActa(acta, new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Acta guardada en Supabase", Toast.LENGTH_SHORT).show();
                    cargarActas();
                    etHora.setText("");
                    etFecha.setText("");
                });
            }

            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void cargarActas() {
        SupabaseClient.getDataFromTable("acta", new Callback() {
            @Override public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    actas.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Acta acta = new Acta(
                                obj.getInt("id_accidente"),
                                obj.getInt("id_audiencia"),
                                obj.getString("hora"),
                                obj.getInt("id_zona"),
                                obj.getInt("id_agente"),
                                obj.getString("fecha")
                        );
                        actas.add(acta);
                    }
                    requireActivity().runOnUiThread(() -> actaAdapter.notifyDataSetChanged());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) { e.printStackTrace(); }
        });
    }
}

*/
