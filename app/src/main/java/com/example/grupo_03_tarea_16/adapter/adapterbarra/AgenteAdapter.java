package com.example.grupo_03_tarea_16.adapter.adapterbarra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;

import java.util.ArrayList;

public class AgenteAdapter extends ArrayAdapter<Agente> {

    private ArrayList<PuesDeControl> listaUbicacion;

    public AgenteAdapter(Context context, ArrayList<Agente> agente, ArrayList<PuesDeControl> puesDeControl) {
        super(context, 0, agente);
        this.listaUbicacion = puesDeControl;
    }

    private String obtenerNombrePuesDeControlPorId(int idPuestoControl) {
        for (PuesDeControl puesDeControl : listaUbicacion) {
            if (puesDeControl.getIdPuestoControl() == idPuestoControl) {
                return puesDeControl.getNombrePuestoControl();
            }
        }
        return "Zona desconocida";
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Agente agente = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_agente, parent, false);
        }

        TextView tvidAgente = convertView.findViewById(R.id.tv_idagente);
        TextView tvcedulaA = convertView.findViewById(R.id.tv_cedulaa);
        TextView tvnombre = convertView.findViewById(R.id.tv_nombre);
        TextView tvidpuestocontrol = convertView.findViewById(R.id.tv_idpuestocontrol);
        TextView tvrango = convertView.findViewById(R.id.tv_rango);


        tvidAgente.setText("Código del Agente: " + agente.getIdAgente());
        tvcedulaA.setText("Cédula del Agente: " + agente.getCedulaA());
        tvnombre.setText("Nombre Completo: " + agente.getNombre());

        String nombreUbicacion = obtenerNombrePuesDeControlPorId(agente.getIdPuestoControl());
        tvidpuestocontrol.setText("Ubicación: " + nombreUbicacion);

        tvrango.setText("Rango: " + agente.getRango());

        return convertView;
    }

}
