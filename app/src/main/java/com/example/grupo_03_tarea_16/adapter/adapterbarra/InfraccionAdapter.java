package com.example.grupo_03_tarea_16.adapter.adapterbarra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Infraccion;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;

import java.util.ArrayList;

public class InfraccionAdapter extends ArrayAdapter<Infraccion> {
    private ArrayList<Agente> listaAgentes;
    private ArrayList<Vehiculo> listaVehiculos;
    private ArrayList<NormasDeT> listaNormas;

    public InfraccionAdapter(Context context, ArrayList<Infraccion> infracciones,
                             ArrayList<Agente> agentes, ArrayList<Vehiculo> vehiculos, ArrayList<NormasDeT> normas) {
        super(context, 0, infracciones);
        this.listaAgentes = agentes;
        this.listaVehiculos = vehiculos;
        this.listaNormas = normas;
    }

    private String obtenerNombreAgentePorId(int idAgente) {
        for (Agente agente : listaAgentes) {
            if (agente.getIdAgente() == idAgente) {
                return agente.getNombre();
            }
        }
        return "Desconocido";
    }

    private String obtenerPlacaPorId(String numPlaca) {
        for (Vehiculo vehiculo : listaVehiculos) {
            if (vehiculo.getNumPlaca().equals(numPlaca)) {
                return vehiculo.getNumPlaca();
            }
        }
        return "Desconocido";
    }

    private String obtenerNormaPorId(int idNorma) {
        for (NormasDeT norma : listaNormas) {
            if (norma.getIdNorma() == idNorma) {
                return norma.getNumNorma();
            }
        }
        return "Desconocido";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Infraccion infraccion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_infraccion, parent, false);
        }

        TextView tvIdInfraccion = convertView.findViewById(R.id.tv_idinfraccion);
        TextView tvAgente = convertView.findViewById(R.id.tv_agente);
        TextView tvNumPlaca = convertView.findViewById(R.id.tv_numplaca);
        TextView tvNorma = convertView.findViewById(R.id.tv_norma);
        TextView tvValorMulta = convertView.findViewById(R.id.tv_valormulta);
        TextView tvFecha = convertView.findViewById(R.id.tv_fecha);
        TextView tvHora = convertView.findViewById(R.id.tv_hora);

        tvIdInfraccion.setText("Código: " + infraccion.getIdInfraccion());
        tvAgente.setText("Agente: " + obtenerNombreAgentePorId(infraccion.getIdAgente()));
        tvNumPlaca.setText("Vehículo: " + obtenerPlacaPorId(infraccion.getNumPlaca()));
        tvNorma.setText("Norma: " + obtenerNormaPorId(infraccion.getIdNorma()));
        tvValorMulta.setText("Multa: $" + infraccion.getValorMulta());
        tvFecha.setText("Fecha: " + infraccion.getFecha());
        tvHora.setText("Hora: " + infraccion.getHora());

        return convertView;
    }
}

