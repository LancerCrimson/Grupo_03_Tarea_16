package com.example.grupo_03_tarea_16.adapter.adapterbarra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Agente;

import java.util.List;

public class FeedAdapter extends ArrayAdapter<Accidente> {

    private List<Agente> agentes;

    public FeedAdapter(Context ctx, List<Accidente> accidentes, List<Agente> agentes) {
        super(ctx, 0, accidentes);
        this.agentes = agentes;
    }

    private String obtenerNombreAgentePorId(int idAgente) {
        for (Agente a : agentes) {
            if (a.getIdAgente() == idAgente) return a.getNombre();
        }
        return "Agente desconocido";
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        Accidente acc = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_accidente_feed, parent, false);
        }

        // Referencias
        TextView tvAgente = convertView.findViewById(R.id.tv_id_agente);
        TextView tvFecha  = convertView.findViewById(R.id.tv_fecha);
        TextView tvHora   = convertView.findViewById(R.id.tv_hora);
        TextView tvDescripcion = convertView.findViewById(R.id.tv_descripcion);
        TextView tvPlaca = convertView.findViewById(R.id.tv_numplaca);
        TextView tvLatitud = convertView.findViewById(R.id.tv_latitud);
        TextView tvLongitud = convertView.findViewById(R.id.tv_longitud);
        ImageView ivFoto = convertView.findViewById(R.id.iv_foto_accidente);

        // Asignación de valores
        tvAgente.setText("Agente: " + obtenerNombreAgentePorId(acc.getIdAgente()));
        tvFecha.setText(acc.getFecha());
        tvHora.setText(acc.getHora());
        tvDescripcion.setText(acc.getDescripcion());
        tvPlaca.setText("Placa: " + acc.getNumPlaca());
        tvLatitud.setText("Latitud: " + acc.getLatitud());
        tvLongitud.setText("Longitud: " + acc.getLongitud());

        // Imagen
        byte[] foto = acc.getMedia();
        if (foto != null && foto.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
            ivFoto.setImageBitmap(bmp);
        } else {
            ivFoto.setImageResource(R.drawable.ic_menu_nofoto);
        }

        return convertView;
    }
}

