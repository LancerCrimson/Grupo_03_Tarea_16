package com.example.grupo_03_tarea_16.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adapterbarra.AccidenteAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccidenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccidenteFragment extends Fragment {

    private static final int REQ_CAMARA = 101;
    private static final int REQ_GALERIA = 102;
    private static final int REQ_UBICACION = 103;

    private Spinner spPlaca, spAgente;
    private TextInputEditText etFecha, etHora, etDescripcion;
    private Button btnFoto, btnUbicacion, btnGuardar;
    private ListView lvAccidentes;

    private byte[] fotoBytes = null;
    private double latitud = 0.0;
    private double longitud = 0.0;

    private DBHelper dbHelper;
    private List<Accidente> lista;
    private AccidenteAdapter adapter;

    private List<Agente> listaAgentes;
    private List<Vehiculo> listaVehiculos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccidenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccidenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccidenteFragment newInstance(String param1, String param2) {
        AccidenteFragment fragment = new AccidenteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accidente, container, false);

        // Referencias UI
        spPlaca = view.findViewById(R.id.sp_placa);
        spAgente = view.findViewById(R.id.sp_agente);
        etFecha = view.findViewById(R.id.et_fecha);
        etHora = view.findViewById(R.id.et_hora);
        etDescripcion = view.findViewById(R.id.et_descripcion);
        btnFoto = view.findViewById(R.id.btn_foto_accidente);
        btnUbicacion = view.findViewById(R.id.btn_ubicacion);
        btnGuardar = view.findViewById(R.id.btn_guardar_accidente);
        lvAccidentes = view.findViewById(R.id.lv_accidentes);

        dbHelper = new DBHelper(requireContext());

        // --- Fecha ---
        etFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(
                    requireContext(),
                    (view12, year, month, dayOfMonth) -> {
                        String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        etFecha.setText(fechaSeleccionada);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.show();
        });

        // --- Hora ---
        etHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(
                    requireContext(),
                    (view1, hourOfDay, minute) -> {
                        String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                        etHora.setText(horaSeleccionada);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            timePicker.show();
        });

        // --- Cargar Spinners ---
        listaVehiculos = dbHelper.get_all_Vehiculo();
        List<String> placas = new ArrayList<>();
        for (Vehiculo v : listaVehiculos) placas.add(v.getNumPlaca());
        spPlaca.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, placas));

        listaAgentes = dbHelper.get_all_Agente();
        List<String> nombresAgentes = new ArrayList<>();
        for (Agente a : listaAgentes) nombresAgentes.add(a.getNombre());
        spAgente.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, nombresAgentes));

        // --- Cargar Accidentes ---
        lista = dbHelper.get_all_Accidente();
        adapter = new AccidenteAdapter(requireContext(), lista);
        lvAccidentes.setAdapter(adapter);

        // --- Foto ---
        btnFoto.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Foto del accidente")
                    .setItems(new CharSequence[]{"Tomar foto", "Galería"}, (dlg, which) -> {
                        Intent intent;
                        if (which == 0) {
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQ_CAMARA);
                        } else {
                            intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, REQ_GALERIA);
                        }
                    })
                    .show();
        });

        // --- Ubicación ---
        btnUbicacion.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(requireContext(), SeleccionarUbicacionActivity.class);
                startActivityForResult(intent, REQ_UBICACION);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireContext(), "Error al abrir el mapa", Toast.LENGTH_SHORT).show();
            }
        });

        // --- Guardar ---
        btnGuardar.setOnClickListener(v -> {
            String placa = (String) spPlaca.getSelectedItem();
            int posAgente = spAgente.getSelectedItemPosition();
            int idAgente = listaAgentes.get(posAgente).getIdAgente();

            String fecha = etFecha.getText().toString().trim();
            String hora = etHora.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (placa == null || fecha.isEmpty() || hora.isEmpty() || descripcion.isEmpty()
                    || latitud == 0 || longitud == 0) {
                Toast.makeText(requireContext(), "Completa todos los campos, incluyendo ubicación", Toast.LENGTH_SHORT).show();
                return;
            }

            Accidente nuevo = new Accidente(0, placa, idAgente, hora, fecha, descripcion,
                    latitud, longitud, fotoBytes);
            dbHelper.InsertarAccidente(nuevo);

            lista.clear();
            lista.addAll(dbHelper.get_all_Accidente());
            adapter.notifyDataSetChanged();

            etFecha.setText("");
            etHora.setText("");
            etDescripcion.setText("");
            fotoBytes = null;
            latitud = 0;
            longitud = 0;

            Toast.makeText(requireContext(), "Accidente registrado", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) return;

        switch (reqCode) {
            case REQ_CAMARA:
                Bitmap fotoCamara = (Bitmap) data.getExtras().get("data");
                if (fotoCamara != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fotoCamara.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    fotoBytes = baos.toByteArray();
                }
                break;

            case REQ_GALERIA:
                try (InputStream is = requireContext().getContentResolver().openInputStream(data.getData())) {
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    fotoBytes = baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case REQ_UBICACION:
                latitud = data.getDoubleExtra("latitud", 0);
                longitud = data.getDoubleExtra("longitud", 0);
                Toast.makeText(requireContext(), "Ubicación seleccionada", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}