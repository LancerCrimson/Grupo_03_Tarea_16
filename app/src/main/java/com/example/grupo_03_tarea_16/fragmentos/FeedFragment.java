package com.example.grupo_03_tarea_16.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.SupabaseClient;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.FeedAdapter;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Agente;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FeedFragment extends Fragment {

    private ListView lvFeed;
    private List<Accidente> listaAccidentes = new ArrayList<>();
    private List<Agente> listaAgentes = new ArrayList<>();
    private FeedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        lvFeed = view.findViewById(R.id.lv_feed_accidentes);
        cargarAgentesYAccidentes();
        return view;
    }

    private void cargarAgentesYAccidentes() {
        // Primero agentes
        SupabaseClient.listarAgentes(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    listaAgentes.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Agente agente = new Agente(obj.getInt("id_agente"), obj.getString("nombre"));
                        listaAgentes.add(agente);
                    }

                    // Luego accidentes
                    SupabaseClient.listarAccidentes(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                JSONArray array = new JSONArray(response.body().string());
                                listaAccidentes.clear();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject o = array.getJSONObject(i);
                                    Accidente acc = new Accidente(
                                            o.getInt("id_accidente"),
                                            o.getString("numplaca"),
                                            o.getInt("id_agente"),
                                            o.getString("hora"),
                                            o.getString("fecha"),
                                            o.getString("descripcion"),
                                            o.getDouble("latitud"),
                                            o.getDouble("longitud"),
                                            o.has("media") && !o.isNull("media") ? android.util.Base64.decode(o.getString("media"), android.util.Base64.NO_WRAP) : null
                                    );
                                    listaAccidentes.add(acc);
                                }

                                requireActivity().runOnUiThread(() -> {
                                    adapter = new FeedAdapter(requireContext(), listaAccidentes, listaAgentes);
                                    lvFeed.setAdapter(adapter);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
