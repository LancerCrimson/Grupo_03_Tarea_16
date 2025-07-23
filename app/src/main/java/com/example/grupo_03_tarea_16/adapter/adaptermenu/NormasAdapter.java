package com.example.grupo_03_tarea_16.adapter.adaptermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;

import java.util.ArrayList;

public class NormasAdapter extends ArrayAdapter<NormasDeT> {

    public NormasAdapter(Context context, ArrayList<NormasDeT> normasDeTS) {
        super(context, 0, normasDeTS);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NormasDeT normasDeT = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_normas, parent, false);
        }

        TextView tvidnorma = convertView.findViewById(R.id.tv_idnorma);
        TextView tvnumnorma = convertView.findViewById(R.id.tv_numnorma);
        TextView tvdescripcion = convertView.findViewById(R.id.tv_descripcion);

        tvidnorma.setText("Cédula: " + normasDeT.getIdNorma());
        tvnumnorma.setText("Nombre: " + normasDeT.getNumNorma());
        tvdescripcion.setText("Ciudad: " + normasDeT.getDescripcion());

        return convertView;
    }

}
