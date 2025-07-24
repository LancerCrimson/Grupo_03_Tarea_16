package com.example.grupo_03_tarea_16.apartadomenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo_03_tarea_16.R;
import com.example.grupo_03_tarea_16.adapter.adaptermenu.VehiculoAdapter;
import com.example.grupo_03_tarea_16.db.DBHelper;
import com.example.grupo_03_tarea_16.modelo.Propietario;
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
 * Use the {@link VehiculoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehiculoFragment extends Fragment {
    private static final int REQ_CAMARA  = 100;
    private static final int REQ_GALERIA = 200;

    private TextInputEditText etPlaca, etMarca, etModelo, etMotor, etYear;
    private Spinner spCedula;
    private Button btnFoto, btnGuardar;
    private ListView lvVehiculos;
    private byte[] fotoBytes = null;

    private DBHelper dbHelper;
    private List<Vehiculo> lista;
    private VehiculoAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VehiculoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehiculoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehiculoFragment newInstance(String param1, String param2) {
        VehiculoFragment fragment = new VehiculoFragment();
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
        View view = inflater.inflate(R.layout.fragment_vehiculo, container, false);

        // 1) Referencias
        etPlaca   = view.findViewById(R.id.et_numplaca);
        etMarca   = view.findViewById(R.id.et_marca);
        etModelo  = view.findViewById(R.id.et_modelo);
        etMotor   = view.findViewById(R.id.et_motor);
        etYear    = view.findViewById(R.id.et_year);
        spCedula  = view.findViewById(R.id.sp_cedulap);
        btnFoto   = view.findViewById(R.id.btn_seleccionar_foto);
        btnGuardar= view.findViewById(R.id.btn_guardar);
        lvVehiculos = view.findViewById(R.id.lv_vehiculos);

        dbHelper = new DBHelper(requireContext());

        // 2) Spinner de cédulas
        List<Propietario> props = dbHelper.get_all_Propietario();
        List<String> cedulas = new ArrayList<>();
        for (Propietario p : props) cedulas.add(p.getCedulaP());
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                cedulas
        );
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCedula.setAdapter(spAdapter);

        // 3) Lista de vehículos
        lista   = dbHelper.get_all_Vehiculo();
        adapter = new VehiculoAdapter(requireContext(), lista);
        lvVehiculos.setAdapter(adapter);

        // 4) Botón foto
        btnFoto.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Foto del Vehículo")
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

        // 5) Guardar vehículo
        btnGuardar.setOnClickListener(v -> {
            String placa  = etPlaca.getText().toString().trim();
            String marca  = etMarca.getText().toString().trim();
            String modelo = etModelo.getText().toString().trim();
            String motor  = etMotor.getText().toString().trim();
            String añoTxt = etYear.getText().toString().trim();
            String cedP   = (String) spCedula.getSelectedItem();

            // ya no chequeamos fotoBytes aquí
            if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty()
                    || motor.isEmpty() || añoTxt.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Completa todos los campos",
                        Toast.LENGTH_LONG).show();
                return;
            }

            int year;
            try {
                year = Integer.parseInt(añoTxt);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(),
                        "Ingresa un año válido",
                        Toast.LENGTH_LONG).show();
                return;
            }

            // si no hay foto, usamos un array vacío
            byte[] media = (fotoBytes != null) ? fotoBytes : new byte[0];

            Vehiculo nuevo = new Vehiculo(placa, marca, modelo, motor, year, media, cedP);
            dbHelper.InsertarVehiculo(nuevo);

            // refrescar lista
            lista.clear();
            lista.addAll(dbHelper.get_all_Vehiculo());
            adapter.notifyDataSetChanged();

            // limpiar formulario
            etPlaca.setText("");
            etMarca.setText("");
            etModelo.setText("");
            etMotor.setText("");
            etYear.setText("");
            fotoBytes = null;

            Toast.makeText(requireContext(), "Vehículo guardado", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) return;

        Bitmap bmp = null;
        if (requestCode == REQ_CAMARA) {
            bmp = (Bitmap) data.getExtras().get("data");
        } else if (requestCode == REQ_GALERIA) {
            try (InputStream is = requireContext()
                    .getContentResolver()
                    .openInputStream(data.getData())) {
                bmp = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            fotoBytes = baos.toByteArray();
        }
    }
}