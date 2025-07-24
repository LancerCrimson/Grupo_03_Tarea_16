package com.example.grupo_03_tarea_16.fragmentos;

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
import com.example.grupo_03_tarea_16.adapter.adapterbarra.InfraccionAdapter;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Infraccion;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InfraccionFragment extends Fragment {

    private TextInputEditText etIdInfraccion, etValorMulta, etFecha, etHora;
    private Spinner spnAgente, spnPlaca, spnNorma;
    private Button btnGuardar;
    private ListView lvInfracciones;

    private ArrayList<Agente> listaAgentes = new ArrayList<>();
    private ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
    private ArrayList<NormasDeT> listaNormas = new ArrayList<>();
    private ArrayList<Infraccion> listaInfracciones = new ArrayList<>();

    private InfraccionAdapter infraccionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infraccion, container, false);

        etIdInfraccion = view.findViewById(R.id.et_idinfraccion);
        etValorMulta = view.findViewById(R.id.et_valormulta);
        etFecha = view.findViewById(R.id.et_fecha);
        etHora = view.findViewById(R.id.et_hora);
        spnAgente = view.findViewById(R.id.spn_idagente);
        spnPlaca = view.findViewById(R.id.spn_numplaca);
        spnNorma = view.findViewById(R.id.spn_idnorma);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvInfracciones = view.findViewById(R.id.lv_infraccion);

        cargarSpinners();
        cargarInfracciones();

        btnGuardar.setOnClickListener(v -> guardarInfraccion());

        return view;
    }

    private void cargarSpinners() {
        SupabaseClient.listarAgentes(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaAgentes.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        listaAgentes.add(new Agente(
                                obj.getInt("id_agente"),
                                obj.getString("cedulaa"),
                                obj.getString("nombre"),
                                obj.getInt("id_puesdecontrol"),
                                obj.getString("rango")
                        ));
                    }
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getNombresAgentes());
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnAgente.setAdapter(adapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });

        SupabaseClient.getVehiculos(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaVehiculos.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        listaVehiculos.add(new Vehiculo(
                                obj.getString("numplaca"),
                                obj.getString("marca"),
                                obj.getString("modelo"),
                                obj.getString("motor"),
                                obj.getInt("year"),
                                null, // media opcional
                                obj.getString("cedulap")
                        ));
                    }
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getPlacasVehiculos());
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnPlaca.setAdapter(adapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });

        SupabaseClient.getNormas(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaNormas.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        listaNormas.add(new NormasDeT(
                                obj.getInt("id_norma"),
                                obj.getString("numnorma"),
                                obj.getString("descripcion")
                        ));
                    }
                    getActivity().runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getNombresNormas());
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnNorma.setAdapter(adapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cargarInfracciones() {
        SupabaseClient.listarInfracciones(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaInfracciones.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        listaInfracciones.add(new Infraccion(
                                obj.getInt("id_infraccion"),
                                obj.getInt("id_agente"),
                                obj.getString("numplaca"),
                                obj.getDouble("valormulta"),
                                obj.getString("fecha"),
                                obj.getInt("id_norma"),
                                obj.getString("hora")
                        ));
                    }
                    getActivity().runOnUiThread(() -> {
                        infraccionAdapter = new InfraccionAdapter(getContext(), listaInfracciones, listaAgentes, listaVehiculos, listaNormas);
                        lvInfracciones.setAdapter(infraccionAdapter);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void guardarInfraccion() {
        try {
            int idAgente = listaAgentes.get(spnAgente.getSelectedItemPosition()).getIdAgente();
            String numPlaca = listaVehiculos.get(spnPlaca.getSelectedItemPosition()).getNumPlaca();
            int idNorma = listaNormas.get(spnNorma.getSelectedItemPosition()).getIdNorma();
            double valorMulta = Double.parseDouble(etValorMulta.getText().toString());
            String fecha = etFecha.getText().toString();
            String hora = etHora.getText().toString();

            Infraccion infraccion = new Infraccion(idAgente, numPlaca, valorMulta, fecha, idNorma, hora);
            SupabaseClient.insertarInfraccion(infraccion, new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Infracción guardada", Toast.LENGTH_SHORT).show();
                        cargarInfracciones();
                    });
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: Verifica los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> getNombresAgentes() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Agente a : listaAgentes) nombres.add(a.getNombre());
        return nombres;
    }

    private ArrayList<String> getPlacasVehiculos() {
        ArrayList<String> placas = new ArrayList<>();
        for (Vehiculo v : listaVehiculos) placas.add(v.getNumPlaca());
        return placas;
    }

    private ArrayList<String> getNombresNormas() {
        ArrayList<String> nombres = new ArrayList<>();
        for (NormasDeT n : listaNormas) nombres.add(n.getNumNorma());
        return nombres;
    }
}