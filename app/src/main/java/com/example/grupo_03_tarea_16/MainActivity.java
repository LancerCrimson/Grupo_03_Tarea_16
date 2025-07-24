package com.example.grupo_03_tarea_16;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_menu= findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);

        ImageView circlePulse = findViewById(R.id.logoImage);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circlePulse, "scaleX", 1f, 2f);
        scaleX.setDuration(1000);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circlePulse, "scaleY", 1f, 2f);
        scaleY.setDuration(1000);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(circlePulse, "alpha", 1f, 0f);
        fadeOut.setDuration(1000);
        fadeOut.setRepeatCount(ObjectAnimator.INFINITE);
        fadeOut.setRepeatMode(ObjectAnimator.RESTART);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, fadeOut);
        animatorSet.start();


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_menu) {
            btn_menu.setEnabled(false);

            LayerDrawable layerDrawable = (LayerDrawable) btn_menu.getBackground();
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
                    Intent intent = new Intent(MainActivity.this, menu.class);
                    startActivity(intent);
                    btn_menu.setEnabled(true);
                }
            });
        }
    }

}