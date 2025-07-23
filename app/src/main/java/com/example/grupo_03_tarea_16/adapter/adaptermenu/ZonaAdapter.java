package com.example.grupo_03_tarea_16.adapter.adaptermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Zona;

import java.util.ArrayList;

public class ZonaAdapter extends ArrayAdapter<Zona> {

    public ZonaAdapter(Context context, ArrayList<Zona> zona) {
        super(context, 0, zona);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zona zona = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_zona, parent, false);
        }

        TextView tvidaudiencia = convertView.findViewById(R.id.tv_idaudiencia);
        TextView tvubicacion = convertView.findViewById(R.id.tv_ubicacion);

        tvidaudiencia.setText("Código de la Zona: " + zona.getIdZona());
        tvubicacion.setText("Ubicación: " + zona.getUbicacion());

        return convertView;
    }

}
