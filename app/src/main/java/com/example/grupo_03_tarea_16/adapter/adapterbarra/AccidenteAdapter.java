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

import java.util.List;

public class AccidenteAdapter extends ArrayAdapter<Accidente> {
    public AccidenteAdapter(Context ctx, List<Accidente> datos) {
        super(ctx, 0, datos);
    }

    @Override
    @SuppressLint("ViewHolder")
    public View getView(int pos, View convertView, ViewGroup parent) {
        Accidente acc = getItem(pos);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_accidente, parent, false);
        }

        TextView tvIdAccidente = convertView.findViewById(R.id.tv_id_accidente);
        TextView tvNumPlaca    = convertView.findViewById(R.id.tv_numplaca);
        TextView tvIdAgente    = convertView.findViewById(R.id.tv_id_agente);
        TextView tvFecha       = convertView.findViewById(R.id.tv_fecha);
        TextView tvHora        = convertView.findViewById(R.id.tv_hora);
        TextView tvDescripcion = convertView.findViewById(R.id.tv_descripcion);
        TextView tvLatitud     = convertView.findViewById(R.id.tv_latitud);
        TextView tvLongitud    = convertView.findViewById(R.id.tv_longitud);
        ImageView ivFoto       = convertView.findViewById(R.id.iv_foto_accidente);

        // Asignar valores a los TextView
        tvIdAccidente.setText("ID Accidente: " + acc.getIdAccidente());
        tvNumPlaca.setText("Placa: " + acc.getNumPlaca());
        tvIdAgente.setText("Agente: " + acc.getIdAgente()); // Mostrar nombre, no ID
        tvFecha.setText("Fecha: " + acc.getFecha());
        tvHora.setText("Hora: " + acc.getHora());
        tvDescripcion.setText("Descripción: " + acc.getDescripcion());
        tvLatitud.setText("Latitud: " + acc.getLatitud());
        tvLongitud.setText("Longitud: " + acc.getLongitud());

        // Foto del accidente
        byte[] imgBytes = acc.getMedia();
        if (imgBytes != null && imgBytes.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            ivFoto.setImageBitmap(bmp);
        } else {
            ivFoto.setImageResource(R.drawable.ic_menu_nofoto);
        }

        return convertView;
    }
}