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
import com.example.grupo_03_tarea_16.adapter.adapterbarra.InfraccionAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Infraccion;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfraccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfraccionFragment extends Fragment {

    private TextInputEditText et_idinfraccion, et_valormulta, et_fecha, et_hora;
    private Spinner spn_idnorma, spn_idagente, spn_numplaca;
    private Button btn_guardar;
    private ListView lv_infraccion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfraccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfraccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfraccionFragment newInstance(String param1, String param2) {
        InfraccionFragment fragment = new InfraccionFragment();
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
        View view = inflater.inflate(R.layout.fragment_infraccion, container, false);

        et_idinfraccion = view.findViewById(R.id.et_idinfraccion);
        et_valormulta = view.findViewById(R.id.et_valormulta);
        et_fecha = view.findViewById(R.id.et_fecha);
        et_hora = view.findViewById(R.id.et_hora);
        spn_idnorma = view.findViewById(R.id.spn_idnorma);
        spn_idagente = view.findViewById(R.id.spn_idagente);
        spn_numplaca = view.findViewById(R.id.spn_numplaca);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_infraccion = view.findViewById(R.id.lv_infraccion);

        DBHelper dbHelper = new DBHelper(getActivity());


        ArrayList<Infraccion> listaInfracciones = dbHelper.get_all_Infraccion();
        ArrayList<Agente> listaAgentes = dbHelper.getAllAgente();
        ArrayList<Vehiculo> listaVehiculos = dbHelper.getAllVehiculo();
        ArrayList<NormasDeT> listaNormas = dbHelper.getAllNorma();

        InfraccionAdapter adapter = new InfraccionAdapter(getContext(), listaInfracciones, listaAgentes, listaVehiculos, listaNormas);
        lv_infraccion.setAdapter(adapter);

        ArrayAdapter<Agente> agenteAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaAgentes);
        agenteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_idagente.setAdapter(agenteAdapter);

        ArrayAdapter<Vehiculo> vehiculoAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaVehiculos);
        vehiculoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_numplaca.setAdapter(vehiculoAdapter);

        ArrayAdapter<NormasDeT> normaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaNormas);
        normaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_idnorma.setAdapter(normaAdapter);

        btn_guardar.setOnClickListener(v -> {
            Agente agenteSeleccionado = (Agente) spn_idagente.getSelectedItem();
            int idAgente = agenteSeleccionado.getIdAgente();

            Vehiculo vehiculoSeleccionado = (Vehiculo) spn_numplaca.getSelectedItem();
            String numPlaca = vehiculoSeleccionado.getNumPlaca();

            NormasDeT normaSeleccionada = (NormasDeT) spn_idnorma.getSelectedItem();
            int idNorma = normaSeleccionada.getIdNorma();

            int idInfraccion = Integer.parseInt(et_idinfraccion.getText().toString().trim());
            double valorMulta = Double.parseDouble(et_valormulta.getText().toString().trim());
            String fecha = et_fecha.getText().toString().trim();
            String hora = et_hora.getText().toString().trim();

            if (!fecha.isEmpty() && !hora.isEmpty()) {
                Infraccion nueva = new Infraccion(idInfraccion, idAgente, numPlaca, valorMulta, fecha, idNorma, hora);
                dbHelper.InsertarInfraccion(nueva);

                listaInfracciones.clear();
                listaInfracciones.addAll(dbHelper.get_all_Infraccion());
                adapter.notifyDataSetChanged();

                et_idinfraccion.setText("");
                et_valormulta.setText("");
                et_fecha.setText("");
                et_hora.setText("");
            }
        });

        return view;
    }
}