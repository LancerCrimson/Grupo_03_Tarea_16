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
import com.example.grupo_03_tarea_16.adapter.adaptermenu.OficinaAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OficinagobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OficinagobFragment extends Fragment {

    private TextInputEditText et_idoficinagob, et_valorvehiculo, et_npoliza, et_ubicacion;
    private Spinner spn_numplaca;
    private Button btn_guardar;
    private ListView lv_oficina;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OficinagobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OficinagobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OficinagobFragment newInstance(String param1, String param2) {
        OficinagobFragment fragment = new OficinagobFragment();
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
        View view = inflater.inflate(R.layout.fragment_oficinagob, container, false);
        et_idoficinagob = view.findViewById(R.id.et_idoficinagob);
        et_valorvehiculo = view.findViewById(R.id.et_valorvehiculo);
        et_npoliza = view.findViewById(R.id.et_npoliza);
        spn_numplaca = view.findViewById(R.id.spn_numplaca);
        et_ubicacion = view.findViewById(R.id.et_ubicacion);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_oficina = view.findViewById(R.id.lv_oficina);

        DBHelper dbHelper = new DBHelper(getContext());

        ArrayList<Vehiculo> listaVehiculos = dbHelper.getAllVehiculo();
        ArrayList<OficinaGob> listaOficina = dbHelper.get_all_OficinaGob();
        OficinaAdapter adapter = new OficinaAdapter(getContext(), listaOficina, listaVehiculos);
        lv_oficina.setAdapter(adapter);

        ArrayAdapter<Vehiculo> vehiculoAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaVehiculos);
        vehiculoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_numplaca.setAdapter(vehiculoAdapter);

        btn_guardar.setOnClickListener(v -> {
            String idoficinagob = et_idoficinagob.getText().toString().trim();
            String valorvehiculo = et_valorvehiculo.getText().toString().trim();
            String npoliza = et_npoliza.getText().toString().trim();
            Vehiculo vehiculoSeleccionado = (Vehiculo) spn_numplaca.getSelectedItem();
            String numPlaca = vehiculoSeleccionado.getNumPlaca();
            String ubicacion = et_ubicacion.getText().toString().trim();
            if (!idoficinagob.isEmpty() && !valorvehiculo.isEmpty() && !npoliza.isEmpty()
                    && !ubicacion.isEmpty()) {
                int id = Integer.parseInt(idoficinagob);
                double valor = Double.parseDouble(valorvehiculo);
                OficinaGob nuevo = new OficinaGob(id, valor, npoliza, numPlaca, ubicacion);
                dbHelper.InsertarOficinaGob(nuevo);
                listaOficina.clear();
                listaOficina.addAll(dbHelper.get_all_OficinaGob());
                adapter.notifyDataSetChanged();
                et_idoficinagob.setText("");
                et_valorvehiculo.setText("");
                et_npoliza.setText("");
                et_ubicacion.setText("");

                Toast.makeText(requireContext(), "Oficina gubernamental registrada", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}