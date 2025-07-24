package com.example.grupo_03_tarea_16.adapter.adapterbarra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Acta;

import java.util.List;

public class ActaAdapter extends ArrayAdapter<Acta> {

    public ActaAdapter(Context ctx, List<Acta> datos) {
        super(ctx, 0, datos);
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int pos, View convertView, ViewGroup parent) {
        Acta acta = getItem(pos);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_acta, parent, false);
        }

        TextView tvIdActa    = convertView.findViewById(R.id.tv_id_acta);
        TextView tvAccidente = convertView.findViewById(R.id.tv_accidente);
        TextView tvAudiencia = convertView.findViewById(R.id.tv_audiencia);
        TextView tvZona      = convertView.findViewById(R.id.tv_zona);
        TextView tvAgente    = convertView.findViewById(R.id.tv_agente);
        TextView tvHora      = convertView.findViewById(R.id.tv_hora);
        TextView tvFecha     = convertView.findViewById(R.id.tv_fecha);

        // Asignar los valores
        tvIdActa.setText("ID Acta: " + acta.getIdActa());
        tvAccidente.setText("Accidente ID: " + acta.getIdAccidente());
        tvAudiencia.setText("Audiencia ID: " + acta.getIdAudiencia());
        tvZona.setText("Zona ID: " + acta.getIdZona());
        tvAgente.setText("Agente ID: " + acta.getIdAgente());
        tvHora.setText("Hora: " + acta.getHora());
        tvFecha.setText("Fecha: " + acta.getFecha());

        return convertView;
    }
}
