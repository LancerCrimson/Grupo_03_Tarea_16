package com.example.grupo_03_tarea_16.apartadomenu;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.PuestoControlAdapter;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
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

public class PuestoDeControlFragment extends Fragment {

    private TextInputEditText et_idpuestocontrol, et_ubicacion;
    private Spinner spn_zona;
    private Button btn_guardar;
    private ListView lv_puestodecontrol;

    private ArrayList<Zona> listaZonas = new ArrayList<>();
    private ArrayList<PuesDeControl> listaPuestos = new ArrayList<>();

    private ArrayAdapter<String> zonaAdapter;
    private PuestoControlAdapter puestoControlAdapter;
    private int idPuestoSeleccionado = -1;

    public PuestoDeControlFragment() {}

    public static PuestoDeControlFragment newInstance() {
        return new PuestoDeControlFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puesto_de_control, container, false);

        et_idpuestocontrol = view.findViewById(R.id.et_idpuestocontrol);
        et_ubicacion = view.findViewById(R.id.et_ubicacion);
        spn_zona = view.findViewById(R.id.spn_zona);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_puestodecontrol = view.findViewById(R.id.lv_puestodecontrol);

        cargarZonas();
        cargarPuestos();

        btn_guardar.setOnClickListener(v -> {
            if (idPuestoSeleccionado == -1) {
                registrarPuesto();
            } else {
                actualizarPuesto();
            }
        });

        lv_puestodecontrol.setOnItemClickListener((parent, view1, position, id) -> {
            PuesDeControl puesto = listaPuestos.get(position);
            et_idpuestocontrol.setText(String.valueOf(puesto.getIdPuestoControl()));
            et_ubicacion.setText(puesto.getUbicacion());
            for (int i = 0; i < listaZonas.size(); i++) {
                if (listaZonas.get(i).getIdZona() == puesto.getIdZona()) {
                    spn_zona.setSelection(i);
                    break;
                }
            }
            idPuestoSeleccionado = puesto.getIdPuestoControl();
        });

        lv_puestodecontrol.setOnItemLongClickListener((parent, view12, position, id) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Eliminar Puesto")
                    .setMessage("¿Estás seguro de eliminar este puesto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        PuesDeControl puesto = listaPuestos.get(position);
                        eliminarPuesto(puesto.getIdPuestoControl());
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        return view;
    }

    private void cargarZonas() {
        SupabaseClient.getZonas(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ZONA", "Error al obtener zonas", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        listaZonas.clear();
                        final ArrayList<String> nombresZonas = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int idZona = obj.getInt("id_zona");
                            String ubicacion = obj.getString("ubicacion");
                            Zona zona = new Zona(idZona, ubicacion);
                            listaZonas.add(zona);
                            nombresZonas.add(ubicacion);
                        }

                        requireActivity().runOnUiThread(() -> {
                            zonaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresZonas);
                            zonaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_zona.setAdapter(zonaAdapter);
                        });
                    } catch (JSONException e) {
                        Log.e("ZONA", "Error al parsear JSON", e);
                    }
                }
            }
        });
    }

    private void cargarPuestos() {
        SupabaseClient.listarPuestosControl(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("PUESTO", "Error al obtener puestos", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        listaPuestos.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int id = obj.getInt("id_puesdecontrol");
                            String ubicacion = obj.getString("ubicacion");
                            int idZona = obj.getInt("id_zona");

                            PuesDeControl puesto = new PuesDeControl(id, idZona, ubicacion);
                            listaPuestos.add(puesto);
                        }

                        requireActivity().runOnUiThread(() -> {
                            puestoControlAdapter = new PuestoControlAdapter(getContext(), listaPuestos, listaZonas);
                            lv_puestodecontrol.setAdapter(puestoControlAdapter);
                        });
                    } catch (JSONException e) {
                        Log.e("PUESTO", "Error al parsear JSON", e);
                    }
                }
            }
        });
    }

    private void registrarPuesto() {
        String ubicacion = et_ubicacion.getText().toString().trim();
        if (ubicacion.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese ubicación", Toast.LENGTH_SHORT).show();
            return;
        }
        int idZona = listaZonas.get(spn_zona.getSelectedItemPosition()).getIdZona();

        PuesDeControl nuevo = new PuesDeControl(idZona, ubicacion);
        SupabaseClient.insertarPuestoControl(nuevo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("REGISTRO", "Error al registrar", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarPuestos();
                });
            }
        });
    }

    private void actualizarPuesto() {
        String nuevaUbicacion = et_ubicacion.getText().toString().trim();
        if (nuevaUbicacion.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese ubicación", Toast.LENGTH_SHORT).show();
            return;
        }
        int idZona = listaZonas.get(spn_zona.getSelectedItemPosition()).getIdZona();

        PuesDeControl actualizado = new PuesDeControl(idPuestoSeleccionado, idZona, nuevaUbicacion);
        SupabaseClient.actualizarPuestoControl(idPuestoSeleccionado, actualizado, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ACTUALIZAR", "Error al actualizar", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarPuestos();
                });
            }
        });
    }

    private void eliminarPuesto(int idPuesto) {
        SupabaseClient.eliminarPuestoControl(idPuesto, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ELIMINAR", "Error al eliminar", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    cargarPuestos();
                });
            }
        });
    }

    private void limpiarCampos() {
        et_idpuestocontrol.setText("");
        et_ubicacion.setText("");
        spn_zona.setSelection(0);
        idPuestoSeleccionado = -1;
    }
}
