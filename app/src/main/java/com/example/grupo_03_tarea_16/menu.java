package com.example.grupo_03_tarea_16;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo_03_tarea_16.databinding.ActivityMenuBinding;

public class menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_maps, R.id.nav_agente, R.id.nav_infraccion,
                R.id.nav_accidente, R.id.nav_acta, R.id.nav_feed, R.id.nav_cerrarsesion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);

        int id = item.getItemId();

        if (id == R.id.action_propietario) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_propietario);
        } else if (id == R.id.action_normas) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_normas);
        } else if (id == R.id.action_oficina) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_oficina);
        }else if (id == R.id.action_audiencia) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_audiencia);
        }else if (id == R.id.action_zona) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_zona);
        }else if (id == R.id.action_puestoControl) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_puestoControl);
        }else if (id == R.id.action_vehiculo) {
            navController.popBackStack(R.id.nav_feed, false);
            navController.navigate(R.id.nav_vehiculo);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}