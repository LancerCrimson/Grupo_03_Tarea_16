package com.example.grupo_03_tarea_16.fragmentos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

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
import com.example.grupo_03_tarea_16.adapter.adapterbarra.ActaAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Acta;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.example.grupo_03_tarea_16.modelo.Zona;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActaFragment extends Fragment {
    private Spinner spAccidente, spAudiencia, spZona, spAgente;
    private TextInputEditText etHora, etFecha;
    private Button btnGuardar;
    private ListView lvActas;

    private DBHelper dbHelper;
    private List<Acta> listaActas;
    private ActaAdapter adapter;

    private List<Accidente> listaAccidentes;
    private List<Audiencia> listaAudiencias;
    private List<Zona> listaZonas;
    private List<Agente> listaAgentes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActaFragment newInstance(String param1, String param2) {
        ActaFragment fragment = new ActaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acta, container, false);

        // Referencias UI
        spAccidente = view.findViewById(R.id.sp_accidente);
        spAudiencia = view.findViewById(R.id.sp_audiencia);
        spZona = view.findViewById(R.id.sp_zona);
        spAgente = view.findViewById(R.id.sp_agente);
        etHora = view.findViewById(R.id.et_hora);
        etFecha = view.findViewById(R.id.et_fecha);
        btnGuardar = view.findViewById(R.id.btn_guardar_acta);
        lvActas = view.findViewById(R.id.lv_actas);

        dbHelper = new DBHelper(requireContext());

        // --- Fecha ---
        etFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(
                    requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        etFecha.setText(fechaSeleccionada);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.show();
        });

        // --- Hora ---
        etHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(
                    requireContext(),
                    (view12, hourOfDay, minute) -> {
                        String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                        etHora.setText(horaSeleccionada);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            timePicker.show();
        });

        // --- Poblar Spinners ---
        listaAccidentes = dbHelper.get_all_Accidente();
        List<String> idsAccidentes = new ArrayList<>();
        for (Accidente a : listaAccidentes)
            idsAccidentes.add(String.valueOf(a.getIdAccidente()));
        spAccidente.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, idsAccidentes));

        listaAudiencias = dbHelper.get_all_Audiencia();
        List<String> idsAudiencias = new ArrayList<>();
        for (Audiencia a : listaAudiencias)
            idsAudiencias.add(String.valueOf(a.getIdAudiencia()));
        spAudiencia.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, idsAudiencias));

        listaZonas = dbHelper.get_all_Zona();
        List<String> nombresZonas = new ArrayList<>();
        for (Zona z : listaZonas)
            nombresZonas.add(z.getNombreZona());
        spZona.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, nombresZonas));

        listaAgentes = dbHelper.get_all_Agente();
        List<String> nombresAgentes = new ArrayList<>();
        for (Agente a : listaAgentes)
            nombresAgentes.add(a.getNombre());
        spAgente.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, nombresAgentes));

        // --- Cargar Actas ---
        listaActas = dbHelper.get_all_Acta();
        adapter = new ActaAdapter(requireContext(), listaActas);
        lvActas.setAdapter(adapter);

        // --- Guardar ---
        btnGuardar.setOnClickListener(v -> {
            int idAccidente = listaAccidentes.get(spAccidente.getSelectedItemPosition()).getIdAccidente();
            int idAudiencia = listaAudiencias.get(spAudiencia.getSelectedItemPosition()).getIdAudiencia();
            int idZona = listaZonas.get(spZona.getSelectedItemPosition()).getIdZona();
            int idAgente = listaAgentes.get(spAgente.getSelectedItemPosition()).getIdAgente();

            String hora = etHora.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();

            if (hora.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Acta nueva = new Acta(0, idAccidente, idAudiencia, hora, idZona, idAgente, fecha);
            dbHelper.InsertarActa(nueva);

            listaActas.clear();
            listaActas.addAll(dbHelper.get_all_Acta());
            adapter.notifyDataSetChanged();

            etHora.setText("");
            etFecha.setText("");
            Toast.makeText(requireContext(), "Acta registrada", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}