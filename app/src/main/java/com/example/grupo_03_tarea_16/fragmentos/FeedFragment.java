package com.example.grupo_03_tarea_16.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.FeedAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Agente;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    private ListView lvFeed;
    private DBHelper dbHelper;
    private List<Accidente> listaAccidentes;
    private List<Agente> listaAgentes;
    private FeedAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        lvFeed = view.findViewById(R.id.lv_feed_accidentes);

        dbHelper = new DBHelper(requireContext());
        listaAccidentes = dbHelper.get_all_Accidente();
        listaAgentes = dbHelper.get_all_Agente();

        // Nuevo adapter que recibe también los agentes
        adapter = new FeedAdapter(requireContext(), listaAccidentes, listaAgentes);
        lvFeed.setAdapter(adapter);

        return view;
    }
}
