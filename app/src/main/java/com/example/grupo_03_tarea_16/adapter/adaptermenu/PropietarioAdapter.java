package com.example.grupo_03_tarea_16.adapter.adaptermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Propietario;

import java.util.ArrayList;

public class PropietarioAdapter extends ArrayAdapter<Propietario> {

    public PropietarioAdapter(Context context, ArrayList<Propietario> propietarios) {
        super(context, 0, propietarios);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Propietario propietario = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_propietario, parent, false);
        }

        TextView tvCedula = convertView.findViewById(R.id.tv_cedula);
        TextView tvNombre = convertView.findViewById(R.id.tv_nombre);
        TextView tvCiudad = convertView.findViewById(R.id.tv_ciudad);

        tvCedula.setText("Cédula: " + propietario.getCedulaP());
        tvNombre.setText("Nombre: " + propietario.getNombre());
        tvCiudad.setText("Ciudad: " + propietario.getCiudad());

        return convertView;
    }

}
