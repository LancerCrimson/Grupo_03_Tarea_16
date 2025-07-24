package com.example.grupo_03_tarea_16.adapter.adaptermenu;

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
import com.example.grupo_03_tarea_16.modelo.Vehiculo;

import java.util.List;

public class VehiculoAdapter extends ArrayAdapter<Vehiculo> {
    public VehiculoAdapter(Context ctx, List<Vehiculo> datos) {
        super(ctx, 0, datos);
    }

    @Override @SuppressLint("ViewHolder")
    public View getView(int pos, View convertView, ViewGroup parent) {
        Vehiculo v = getItem(pos);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_vehiculo, parent, false);
        }

        TextView tvPlaca   = convertView.findViewById(R.id.tv_placa);
        TextView tvMarca   = convertView.findViewById(R.id.tv_marca);
        TextView tvModelo  = convertView.findViewById(R.id.tv_modelo);
        TextView tvMotor   = convertView.findViewById(R.id.tv_motor);
        TextView tvYear    = convertView.findViewById(R.id.tv_year);
        TextView tvCedulaP = convertView.findViewById(R.id.tv_cedula_propietario);
        ImageView ivFoto   = convertView.findViewById(R.id.iv_foto_vehiculo);

        tvPlaca  .setText("Placa: "  + v.getNumPlaca());
        tvMarca  .setText("Marca: "  + v.getMarca());
        tvModelo .setText("Modelo: "+ v.getModelo());
        tvMotor  .setText("Motor: " + v.getMotor());
        tvYear   .setText("Año: "   + v.getYear());
        tvCedulaP.setText("Cédula: "+ v.getCedulaP());

        // cargar foto si existe
        byte[] imgBytes = v.getMedia();
        if (imgBytes != null && imgBytes.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            ivFoto.setImageBitmap(bmp);
        } else {
            ivFoto.setImageResource(R.drawable.ic_menu_nofoto);
        }

        return convertView;
    }
}
