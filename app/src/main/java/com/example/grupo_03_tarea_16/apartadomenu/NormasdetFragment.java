package com.example.grupo_03_tarea_16.apartadomenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.NormasAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NormasdetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormasdetFragment extends Fragment {

    private TextInputEditText et_idnorma, et_numnorma, et_descripcion;
    private Button btn_guardar;
    private ListView lv_normas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NormasdetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NormasdetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NormasdetFragment newInstance(String param1, String param2) {
        NormasdetFragment fragment = new NormasdetFragment();
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
        View view = inflater.inflate(R.layout.fragment_normasdet, container, false);

        et_idnorma = view.findViewById(R.id.et_idnorma);
        et_numnorma = view.findViewById(R.id.et_numnorma);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        btn_guardar = view.findViewById(R.id.btn_guardar);
        lv_normas = view.findViewById(R.id.lv_normas);
        DBHelper dbHelper = new DBHelper(getContext());
        ArrayList<NormasDeT> listaNormas = dbHelper.get_all_Norma();
        NormasAdapter adapter = new NormasAdapter(getContext(), listaNormas);
        lv_normas.setAdapter(adapter);

        btn_guardar.setOnClickListener(v -> {
            String idnorma = et_idnorma.getText().toString().trim();
            String numnorma = et_numnorma.getText().toString().trim();
            String descripcion = et_descripcion.getText().toString().trim();

            if (!idnorma.isEmpty() && !numnorma.isEmpty() && !descripcion.isEmpty()) {
                int idnormaS = Integer.parseInt(idnorma);
                NormasDeT nuevo = new NormasDeT(idnormaS, numnorma, descripcion);
                dbHelper.InsertarNorma(nuevo);
                listaNormas.clear();
                listaNormas.addAll(dbHelper.get_all_Norma());
                adapter.notifyDataSetChanged();
                et_idnorma.setText("");
                et_numnorma.setText("");
                et_descripcion.setText("");

                Toast.makeText(requireContext(), "Norma registrada", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}