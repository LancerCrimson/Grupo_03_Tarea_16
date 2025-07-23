package com.example.grupo_03_tarea_16.adapter.adaptermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;

import java.util.ArrayList;

public class AudienciaAdapter extends ArrayAdapter<Audiencia> {

    public AudienciaAdapter(Context context, ArrayList<Audiencia> audiencia) {
        super(context, 0, audiencia);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Audiencia audiencia = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_audiencia, parent, false);
        }

        TextView tvidaudiencia = convertView.findViewById(R.id.tv_idaudiencia);
        TextView tvlugar = convertView.findViewById(R.id.tv_lugar);
        TextView tvfecha = convertView.findViewById(R.id.tv_fecha);
        TextView tvhora = convertView.findViewById(R.id.tv_hora);

        tvidaudiencia.setText("Código de la audiencia: " + audiencia.getIdAudiencia());
        tvlugar.setText("Lugar: " + audiencia.getLugar());
        tvfecha.setText("Fecha: " + audiencia.getFecha());
        tvhora.setText("Hora: " + audiencia.getHora());

        return convertView;
    }

}
