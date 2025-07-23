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

import java.util.ArrayList;

public class OficinaAdapter extends ArrayAdapter<OficinaGob> {

    public OficinaAdapter(Context context, ArrayList<OficinaGob> oficinaGob) {
        super(context, 0, oficinaGob);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OficinaGob oficinaGob = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_oficina, parent, false);
        }

        TextView tvidoficinagob = convertView.findViewById(R.id.tv_idoficinagob);
        TextView tvvalorvehiculo = convertView.findViewById(R.id.tv_valorvehiculo);
        TextView tvnpoliza = convertView.findViewById(R.id.tv_npoliza);
        TextView tvnumplaca = convertView.findViewById(R.id.tv_numplaca);
        TextView tvubicacion = convertView.findViewById(R.id.tv_ubicacion);

        tvidoficinagob.setText("Código de oficina gubernamental: " + oficinaGob.getIdOficinaGob());
        tvvalorvehiculo.setText("Valor del vehículo: " + oficinaGob.getValorVehiculo());
        tvnpoliza.setText("Número de póliza: " + oficinaGob.getnPoliza());
        tvnumplaca.setText("Número de placa: " + oficinaGob.getNumPlaca());
        tvubicacion.setText("Ubicación: " + oficinaGob.getUbicacion());

        return convertView;
    }

}
