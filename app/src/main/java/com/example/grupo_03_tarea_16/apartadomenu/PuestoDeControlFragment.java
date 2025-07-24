package com.example.grupo_03_tarea_16.apartadomenu;

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
import com.example.grupo_03_tarea_16.adapter.adaptermenu.PuestoControlAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Zona;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PuestoDeControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PuestoDeControlFragment extends Fragment {

    private TextInputEditText et_idpuestocontrol, et_ubicacion;
    private Spinner spn_zona;
    private Button btn_guardar;
    private ListView lv_puestodecontrol;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PuestoDeControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PuestoDeControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PuestoDeControlFragment newInstance(String param1, String param2) {
        PuestoDeControlFragment fragment = new PuestoDeControlFragment();
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
        View view = inflater.inflate(R.layout.fragment_puesto_de_control, container, false);

        et_idpuestocontrol = view.findViewById(R.id.et_idpuestocontrol);
        et_ubicacion = view.findViewById(R.id.et_ubicacion);
        spn_zona = view.findViewById(R.id.spn_zona);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_puestodecontrol = view.findViewById(R.id.lv_puestodecontrol);

        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<Zona> listaZona = dbHelper.getAllZonas();
        ArrayList<PuesDeControl> puesDeControl = dbHelper.get_all_PuestoDeControl();

        PuestoControlAdapter puestoControlAdapter = new PuestoControlAdapter(getContext(), puesDeControl, listaZona);
        lv_puestodecontrol.setAdapter(puestoControlAdapter);

        ArrayList<Zona> listaZonas = dbHelper.getAllZonas();
        ArrayAdapter<Zona> zonaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaZonas);
        zonaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_zona.setAdapter(zonaAdapter);

        btn_guardar.setOnClickListener( v -> {
            Zona zonaSeleccionada = (Zona) spn_zona.getSelectedItem();
            int idZona = zonaSeleccionada.getIdZona();
            int idPuestoControl = Integer.parseInt(et_idpuestocontrol.getText().toString().trim());
            String ubicacion = et_ubicacion.getText().toString().trim();

            if (!ubicacion.isEmpty()) {
                PuesDeControl nuevo = new PuesDeControl(idPuestoControl, idZona, ubicacion);
                dbHelper.InsertarPuestoDeControl(nuevo);

                puesDeControl.clear();
                puesDeControl.addAll(dbHelper.get_all_PuestoDeControl());
                puestoControlAdapter.notifyDataSetChanged();
                et_idpuestocontrol.setText("");
                et_ubicacion.setText("");

                Toast.makeText(requireContext(), "Puesto de Control registrado", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}