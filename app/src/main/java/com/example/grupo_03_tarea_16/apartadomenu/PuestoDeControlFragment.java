package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private PuestoControlAdapter adapter;

    private int idEditando = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puesto_de_control, container, false);

        et_idpuestocontrol = view.findViewById(R.id.et_idpuestocontrol);
        et_ubicacion = view.findViewById(R.id.et_ubicacion);
        spn_zona = view.findViewById(R.id.spn_zona);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_puestodecontrol = view.findViewById(R.id.lv_puestodecontrol);

        cargarZonas();
        cargarPuestos();

        btn_guardar.setOnClickListener(v -> {
            int idZona = listaZonas.get(spn_zona.getSelectedItemPosition()).getIdZona();
            String ubicacion = et_ubicacion.getText().toString();

            if (ubicacion.isEmpty()) {
                Toast.makeText(getContext(), "Completa los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            PuesDeControl puesto = new PuesDeControl(idZona, ubicacion);

            if (idEditando == -1) {
                SupabaseClient.insertarPuestoControl(puesto, callback("insertado"));
            } else {
                SupabaseClient.actualizarPuestoControl(idEditando, puesto, callback("actualizado"));
                idEditando = -1;
            }
        });

        lv_puestodecontrol.setOnItemClickListener((parent, view1, position, id) -> {
            PuesDeControl p = listaPuestos.get(position);
            et_idpuestocontrol.setText(String.valueOf(p.getIdPuestoControl()));
            et_ubicacion.setText(p.getUbicacion());
            for (int i = 0; i < listaZonas.size(); i++) {
                if (listaZonas.get(i).getIdZona() == p.getIdZona()) {
                    spn_zona.setSelection(i);
                    break;
                }
            }
            idEditando = p.getIdPuestoControl();
        });

        lv_puestodecontrol.setOnItemLongClickListener((parent, view12, position, id) -> {
            PuesDeControl p = listaPuestos.get(position);
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("¿Eliminar?")
                    .setMessage("¿Deseas eliminar este puesto de control?")
                    .setPositiveButton("Sí", (dialog, which) -> SupabaseClient.eliminarPuestoControl(p.getIdPuestoControl(), callback("eliminado")))
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        return view;
    }

    private Callback callback(String accion) {
        return new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error al " + accion, Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Puesto " + accion + " correctamente", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                        cargarPuestos();
                    });
                }
            }
        };
    }

    private void cargarZonas() {
        SupabaseClient.getZonas(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error cargando zonas", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String res = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        listaZonas.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            Zona z = new Zona(o.getInt("id_zona"), o.getString("ubicacion"));
                            listaZonas.add(z);
                        }
                        requireActivity().runOnUiThread(() -> {
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
                            for (Zona z : listaZonas) spinnerAdapter.add(z.getNombreZona());
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spn_zona.setAdapter(spinnerAdapter);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void cargarPuestos() {
        SupabaseClient.listarPuestosControl(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error cargando puestos", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String res = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        listaPuestos.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject o = jsonArray.getJSONObject(i);
                            PuesDeControl p = new PuesDeControl(
                                    o.getInt("id_puesdecontrol"),
                                    o.getInt("id_zona"),
                                    o.getString("ubicacion")
                            );
                            listaPuestos.add(p);
                        }
                        requireActivity().runOnUiThread(() -> {
                            adapter = new PuestoControlAdapter(requireContext(), listaPuestos, listaZonas);
                            lv_puestodecontrol.setAdapter(adapter);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void limpiarCampos() {
        et_idpuestocontrol.setText("");
        et_ubicacion.setText("");
        spn_zona.setSelection(0);
    }
}