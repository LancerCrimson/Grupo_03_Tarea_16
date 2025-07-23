package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.PropietarioAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropietarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropietarioFragment extends Fragment {

    private TextInputEditText etCedula, etNombre, etCiudad;
    private Button btnGuardar;
    private ListView lvPropietarios;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PropietarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PropietarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PropietarioFragment newInstance(String param1, String param2) {
        PropietarioFragment fragment = new PropietarioFragment();
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
        View view = inflater.inflate(R.layout.fragment_propietario, container, false);

        etCedula = view.findViewById(R.id.et_cedula);
        etNombre = view.findViewById(R.id.et_nombre);
        etCiudad = view.findViewById(R.id.et_ciudad);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        lvPropietarios = view.findViewById(R.id.lv_propietarios);

        DBHelper dbHelper = new DBHelper(getContext());

        ArrayList<Propietario> listaPropietarios = dbHelper.get_all_Propietario();
        PropietarioAdapter adapter = new PropietarioAdapter(getContext(), listaPropietarios);
        lvPropietarios.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> {
            String cedula = etCedula.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String ciudad = etCiudad.getText().toString().trim();

            if (!cedula.isEmpty() && !nombre.isEmpty() && !ciudad.isEmpty()) {
                Propietario nuevo = new Propietario(cedula, nombre, ciudad);
                dbHelper.InsertarPropietario(nuevo);

                listaPropietarios.clear();
                listaPropietarios.addAll(dbHelper.get_all_Propietario());
                adapter.notifyDataSetChanged();

                etCedula.setText("");
                etNombre.setText("");
                etCiudad.setText("");
            }
        });

        return view;
    }
}