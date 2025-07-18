package com.example.grupo_03_tarea_16.fragmentos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grupo_03_tarea_16.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;


    private final OnMapReadyCallback callback = googleMap -> {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        inicializarMapaConUbicacion();
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private final ActivityResultLauncher<String> permisoUbicacionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    inicializarMapaConUbicacion();
                } else {
                    LatLng ubicacionDefecto = new LatLng(-11.1069, -77.6097);
                    if (mMap != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionDefecto, 13f));
                    }
                }
            });

    private void inicializarMapaConUbicacion() {
        if (mMap == null) return;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(miUbicacion).title("Estoy aquí"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 17));
                        } else {
                            mostrarUbicacionDefecto();
                        }
                    });
        } else {
            permisoUbicacionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void mostrarUbicacionDefecto() {
        if (mMap != null) {
            LatLng ubicacionDefecto = new LatLng(-11.1069, -77.6097);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionDefecto, 13f));
        }
    }


}