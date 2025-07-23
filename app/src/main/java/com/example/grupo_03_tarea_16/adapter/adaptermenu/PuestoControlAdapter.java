package com.example.grupo_03_tarea_16.adapter.adaptermenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Zona;

import java.util.ArrayList;
import java.util.Map;

public class PuestoControlAdapter extends ArrayAdapter<PuesDeControl> {

    private ArrayList<Zona> listaZonas;

    public PuestoControlAdapter(Context context, ArrayList<PuesDeControl> puesDeControl, ArrayList<Zona> zonas) {
        super(context, 0, puesDeControl);
        this.listaZonas = zonas;
    }

    private String obtenerNombreZonaPorId(int idZona) {
        for (Zona zona : listaZonas) {
            if (zona.getIdZona() == idZona) {
                return zona.getNombreZona();
            }
        }
        return "Zona desconocida";
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PuesDeControl puesDeControl = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_puestodecontrol, parent, false);
        }

        TextView tvidpuestocontrol = convertView.findViewById(R.id.tv_idpuestocontrol);
        TextView tvzona = convertView.findViewById(R.id.tv_zona);
        TextView tvubicacion = convertView.findViewById(R.id.tv_ubicacion);

        tvidpuestocontrol.setText("Código del Puesto de Control: " + puesDeControl.getIdPuestoControl());

        String nombreZona = obtenerNombreZonaPorId(puesDeControl.getIdZona());
        tvzona.setText("Zona: " + nombreZona);

        tvubicacion.setText("Ubicación: " + puesDeControl.getUbicacion());

        return convertView;
    }

}
