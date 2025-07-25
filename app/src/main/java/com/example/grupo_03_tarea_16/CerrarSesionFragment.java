package com.example.grupo_03_tarea_16;


import android.content.Intent;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.AppCompatButton;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CerrarSesionFragment extends Fragment {

    public CerrarSesionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout
        return inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        AppCompatButton btnCancelar = view.findViewById(R.id.btnCancelar);

        btnCerrarSesion.setOnClickListener(v -> {

            btnCerrarSesion.setEnabled(false);

            LayerDrawable layerDrawable = (LayerDrawable) btnCerrarSesion.getBackground();
            final Drawable fillDrawable = layerDrawable.findDrawableByLayerId(R.id.fill_layer);
            fillDrawable.setAlpha(10);

            ValueAnimator fillAnim = ValueAnimator.ofInt(10, 255);
            fillAnim.setDuration(800);
            fillAnim.addUpdateListener(animation -> {
                int alpha = (int) animation.getAnimatedValue();
                fillDrawable.setAlpha(alpha);
            });

            fillAnim.start();
            fillAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    btnCerrarSesion.setEnabled(true);
                }
            });

        });

        btnCancelar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}
