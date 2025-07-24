package com.example.grupo_03_tarea_16.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.AgenteAdapter;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.PuestoControlAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Zona;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgenteFragment extends Fragment {

    private TextInputEditText etIdAgente, etCedulaA, etNombre, etRango;
    private Spinner spnPuestoControl;
    private Button btnGuardar;
    private ListView lvagente;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgenteFragment newInstance(String param1, String param2) {
        AgenteFragment fragment = new AgenteFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agente, container, false);

        etIdAgente = view.findViewById(R.id.et_idagente);
        etCedulaA = view.findViewById(R.id.et_cedulaa);
        etNombre = view.findViewById(R.id.et_nombre);
        spnPuestoControl = view.findViewById(R.id.spn_puestocontrol);
        etRango = view.findViewById(R.id.et_rango);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvagente = view.findViewById(R.id.lv_agente);

        DBHelper dbHelper = new DBHelper(getActivity());

        ArrayList<PuesDeControl> listaPuesDeControl = dbHelper.getAllPuestosControl();
        ArrayList<Agente> agente = dbHelper.get_all_Agente();

        AgenteAdapter agenteAdapter = new AgenteAdapter(getContext(), agente, listaPuesDeControl);
        lvagente.setAdapter(agenteAdapter);

        ArrayList<PuesDeControl> listapuestoDeControl = dbHelper.getAllPuestosControl();
        ArrayAdapter<PuesDeControl> puestoControlAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listapuestoDeControl);
        puestoControlAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPuestoControl.setAdapter(puestoControlAdapter);

        btnGuardar.setOnClickListener( v -> {
            PuesDeControl puestoSeleccionada = (PuesDeControl) spnPuestoControl.getSelectedItem();
            int idPuestoControl = puestoSeleccionada.getIdPuestoControl();
            int idAgente = Integer.parseInt(etIdAgente.getText().toString().trim());
            String cedulaA = etCedulaA.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String rango = etRango.getText().toString().trim();

            if (!cedulaA.isEmpty() && !nombre.isEmpty() && !rango.isEmpty()) {
                Agente nuevo = new Agente(idAgente, cedulaA, nombre,idPuestoControl,rango);
                dbHelper.InsertarAgente(nuevo);

                agente.clear();
                agente.addAll(dbHelper.get_all_Agente());
                agenteAdapter.notifyDataSetChanged();
                etIdAgente.setText("");
                etCedulaA.setText("");
                etNombre.setText("");
                etRango.setText("");

            }
        });

        return view;
    }
}