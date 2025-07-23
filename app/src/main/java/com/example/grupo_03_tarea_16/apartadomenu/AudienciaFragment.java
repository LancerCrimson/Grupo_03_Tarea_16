package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.AudienciaAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudienciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudienciaFragment extends Fragment {

    private TextInputEditText et_idaudiencia, et_lugar, et_fecha, et_hora;
    private Button btn_guardar;
    private ListView lv_audiencia;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AudienciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudienciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudienciaFragment newInstance(String param1, String param2) {
        AudienciaFragment fragment = new AudienciaFragment();
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
        View view = inflater.inflate(R.layout.fragment_audiencia, container, false);
        et_idaudiencia = view.findViewById(R.id.et_idaudiencia);
        et_lugar = view.findViewById(R.id.et_lugar);
        et_fecha = view.findViewById(R.id.et_fecha);
        et_hora = view.findViewById(R.id.et_hora);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_audiencia = view.findViewById(R.id.lv_audiencia);

        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<Audiencia> listaAudiencia = dbHelper.get_all_Audiencia();
        AudienciaAdapter adapter = new AudienciaAdapter(getContext(), listaAudiencia);
        lv_audiencia.setAdapter(adapter);
        btn_guardar.setOnClickListener(v -> {
            String idaudiencia = et_idaudiencia.getText().toString().trim();
            String lugar = et_lugar.getText().toString().trim();
            String fecha = et_fecha.getText().toString().trim();
            String hora = et_hora.getText().toString().trim();
            if (!idaudiencia.isEmpty() && !lugar.isEmpty() && !fecha.isEmpty() && !hora.isEmpty()) {
                int idaudienciaS = Integer.parseInt(idaudiencia);
                Audiencia nueva = new Audiencia(idaudienciaS, lugar, fecha, hora);
                dbHelper.InsertarAudiencia(nueva);
                listaAudiencia.clear();
                listaAudiencia.addAll(dbHelper.get_all_Audiencia());
                adapter.notifyDataSetChanged();
                et_idaudiencia.setText("");
                et_lugar.setText("");
                et_fecha.setText("");
                et_hora.setText("");
            }
        });

        return view;
    }
}