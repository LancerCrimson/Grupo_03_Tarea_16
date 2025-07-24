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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.AgenteAdapter;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AgenteFragment extends Fragment {

    private TextInputEditText et_idagente, et_cedulaa, et_nombre, et_rango;
    private Spinner spn_puestocontrol;
    private Button btn_guardar;
    private ListView lv_agente;

    private ArrayList<Agente> listaAgentes = new ArrayList<>();
    private ArrayList<PuesDeControl> listaPuestos = new ArrayList<>();
    private AgenteAdapter adapter;
    private int idAgenteSeleccionado = -1;

    public AgenteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_idagente = view.findViewById(R.id.et_idagente);
        et_cedulaa = view.findViewById(R.id.et_cedulaa);
        et_nombre = view.findViewById(R.id.et_nombre);
        et_rango = view.findViewById(R.id.et_rango);
        spn_puestocontrol = view.findViewById(R.id.spn_puestocontrol);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_agente = view.findViewById(R.id.lv_agente);

        adapter = new AgenteAdapter(requireContext(), listaAgentes, listaPuestos);
        lv_agente.setAdapter(adapter);

        cargarPuestosControl();
        listarAgentes();

        btn_guardar.setOnClickListener(v -> guardarOActualizarAgente());

        lv_agente.setOnItemClickListener((parent, view1, position, id) -> {
            Agente agente = listaAgentes.get(position);
            idAgenteSeleccionado = agente.getIdAgente();
            et_idagente.setText(String.valueOf(agente.getIdAgente()));
            et_cedulaa.setText(agente.getCedulaA());
            et_nombre.setText(agente.getNombre());
            et_rango.setText(agente.getRango());

            for (int i = 0; i < listaPuestos.size(); i++) {
                if (listaPuestos.get(i).getIdPuestoControl() == agente.getIdPuestoControl()) {
                    spn_puestocontrol.setSelection(i);
                    break;
                }
            }
        });

        lv_agente.setOnItemLongClickListener((parent, view12, position, id) -> {
            Agente agente = listaAgentes.get(position);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar agente")
                    .setMessage("¿Seguro que deseas eliminar a " + agente.getNombre() + "?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        SupabaseClient.eliminarAgente(agente.getIdAgente(), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                requireActivity().runOnUiThread(() ->
                                        Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Agente eliminado", Toast.LENGTH_SHORT).show();
                                    listarAgentes();
                                });
                            }
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });
    }

    private void cargarPuestosControl() {
        SupabaseClient.listarPuestosControl(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        listaPuestos.clear();
                        ArrayList<String> nombres = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int id = obj.getInt("id_puesdecontrol");
                            int idZona = obj.getInt("id_zona");
                            String ubicacion = obj.getString("ubicacion");
                            listaPuestos.add(new PuesDeControl(id, idZona, ubicacion));
                            nombres.add(ubicacion);
                        }

                        requireActivity().runOnUiThread(() -> {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombres);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_puestocontrol.setAdapter(adapter);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void listarAgentes() {
        SupabaseClient.listarAgentes(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        listaAgentes.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int id = obj.getInt("id_agente");
                            String cedula = obj.getString("cedulaa");
                            String nombre = obj.getString("nombre");
                            int idPuesto = obj.getInt("id_puesdecontrol");
                            String rango = obj.getString("rango");

                            listaAgentes.add(new Agente(id, cedula, nombre, idPuesto, rango));
                        }

                        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void guardarOActualizarAgente() {
        String cedula = et_cedulaa.getText().toString();
        String nombre = et_nombre.getText().toString();
        String rango = et_rango.getText().toString();
        int indexPuesto = spn_puestocontrol.getSelectedItemPosition();

        if (cedula.isEmpty() || nombre.isEmpty() || rango.isEmpty() || indexPuesto == -1) {
            Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idPuesto = listaPuestos.get(indexPuesto).getIdPuestoControl();
        Agente agente = new Agente(0, cedula, nombre, idPuesto, rango);

        if (idAgenteSeleccionado == -1) {
            SupabaseClient.insertarAgente(agente, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    requireActivity().runOnUiThread(() -> {
                        limpiarCampos();
                        listarAgentes();
                        Toast.makeText(requireContext(), "Agente registrado", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            SupabaseClient.actualizarAgente(idAgenteSeleccionado, agente, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    requireActivity().runOnUiThread(() -> {
                        limpiarCampos();
                        listarAgentes();
                        Toast.makeText(requireContext(), "Agente actualizado", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }

    private void limpiarCampos() {
        et_idagente.setText("");
        et_cedulaa.setText("");
        et_nombre.setText("");
        et_rango.setText("");
        spn_puestocontrol.setSelection(0);
        idAgenteSeleccionado = -1;
    }
}
