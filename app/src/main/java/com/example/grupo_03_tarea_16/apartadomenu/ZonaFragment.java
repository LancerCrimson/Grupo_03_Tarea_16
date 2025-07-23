package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.ZonaAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Zona;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZonaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZonaFragment extends Fragment {

    private TextInputEditText et_idzona, et_ubicacion;
    private Button btn_guardar;
    private ListView lv_zona;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ZonaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZonaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZonaFragment newInstance(String param1, String param2) {
        ZonaFragment fragment = new ZonaFragment();
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
        View view = inflater.inflate(R.layout.fragment_zona, container, false);

        et_idzona = view.findViewById(R.id.et_idzona);
        et_ubicacion = view.findViewById(R.id.et_ubicacion);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_zona = view.findViewById(R.id.lv_zona);

        DBHelper dbHelper = new DBHelper(getContext());

        ArrayList<Zona> zona = dbHelper.get_all_Zona();
        ZonaAdapter zonaAdapter = new ZonaAdapter(getContext(), zona);
        lv_zona.setAdapter(zonaAdapter);

        btn_guardar.setOnClickListener( v -> {
            String idzona = et_idzona.getText().toString().trim();
            String ubicacion = et_ubicacion.getText().toString().trim();
            if (!idzona.isEmpty() && !ubicacion.isEmpty()) {
                int idzonaS = Integer.parseInt(idzona);
                Zona nueva = new Zona(idzonaS, ubicacion);
                dbHelper.InsertarZona(nueva);
                zona.clear();
                zona.addAll(dbHelper.get_all_Zona());
                zonaAdapter.notifyDataSetChanged();
                et_idzona.setText("");
                et_ubicacion.setText("");
            }
        });

        return view;
    }
}