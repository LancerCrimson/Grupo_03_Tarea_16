package com.example.grupo_03_tarea_16;

import android.content.Context;
import android.net.Uri;

import com.example.grupo_03_tarea_16.modelo.Accidente;
import com.example.grupo_03_tarea_16.modelo.Acta;
import com.example.grupo_03_tarea_16.modelo.Agente;
import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.example.grupo_03_tarea_16.modelo.Infraccion;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.example.grupo_03_tarea_16.modelo.Zona;


import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.util.Base64;
import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import okhttp3.ResponseBody;


public class SupabaseClient {

    private static final String SUPABASE_URL = "https://wqpmwrgkkgfzdpsiyxhq.supabase.co/rest/v1";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndxcG13cmdra2dmemRwc2l5eGhxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMwMjM5OTYsImV4cCI6MjA2ODU5OTk5Nn0.WAXHiheRYrutcun7hcXlAJ8JGk2wacvqZnyjkfeqPKo";

    private static final String SUPABASE_STORAGE_URL = SUPABASE_URL + "/storage/v1/object";
    private static final String SUPABASE_STORAGE_PUBLIC_URL = SUPABASE_URL + "/storage/v1/object/public";


    private static final OkHttpClient client = new OkHttpClient();

    public static void getDataFromTable(String tableName, Callback callback) {
        String url = SUPABASE_URL + "/" + tableName + "?select=*"; // trae todos los datos

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    // Propietario

    public static void insertPropietario(Propietario propietario, Callback callback) {
        String url = SUPABASE_URL + "/propietario";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cedulap", propietario.getCedulaP());
            jsonObject.put("nombre", propietario.getNombre());
            jsonObject.put("ciudad", propietario.getCiudad());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public static void updatePropietario(String cedulaP, Propietario propietario, Callback callback) {
        String url = SUPABASE_URL + "/propietario?cedulap=eq." + cedulaP;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", propietario.getNombre());
            jsonObject.put("ciudad", propietario.getCiudad());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body) // PATCH es para actualizar
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation") // Para obtener respuesta si se desea
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public static void deletePropietario(String cedulaP, Callback callback) {
        String url = SUPABASE_URL + "/propietario?cedulap=eq." + cedulaP;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation") // opcional
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void getPropietarios(Callback callback) {
        String url = SUPABASE_URL + "/propietario?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    // ===================== ZONA ===================== //

    public static void insertZona(Zona zona, Callback callback) {
        String url = SUPABASE_URL + "/zona";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ubicacion", zona.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void updateZona(int idZona, Zona zona, Callback callback) {
        String url = SUPABASE_URL + "/zona?id_zona=eq." + idZona;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ubicacion", zona.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void deleteZona(int idZona, Callback callback) {
        String url = SUPABASE_URL + "/zona?id_zona=eq." + idZona;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public static void getZonas(Callback callback) {
        String url = SUPABASE_URL + "/zona?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    // ===================== AUDIENCIA ===================== //

    public static void insertAudiencia(Audiencia audiencia, Callback callback) {
        String url = SUPABASE_URL + "/audiencia";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lugar", audiencia.getLugar());
            jsonObject.put("fecha", audiencia.getFecha());
            jsonObject.put("hora", audiencia.getHora());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")

                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void updateAudiencia(int idAudiencia, Audiencia audiencia, Callback callback) {
        String url = SUPABASE_URL + "/audiencia?id_audiencia=eq." + idAudiencia;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lugar", audiencia.getLugar());
            jsonObject.put("fecha", audiencia.getFecha());
            jsonObject.put("hora", audiencia.getHora());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void deleteAudiencia(int idAudiencia, Callback callback) {
        String url = SUPABASE_URL + "/audiencia?id_audiencia=eq." + idAudiencia;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void getAudiencias(Callback callback) {
        String url = SUPABASE_URL + "/audiencia?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    // ===================== NORMASDET ===================== //

    public static void insertNorma(NormasDeT norma, Callback callback) {
        String url = SUPABASE_URL + "/normasdet";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("numnorma", norma.getNumNorma());
            jsonObject.put("descripcion", norma.getDescripcion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void updateNorma(int idNorma, NormasDeT norma, Callback callback) {
        String url = SUPABASE_URL + "/normasdet?id_norma=eq." + idNorma;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("numnorma", norma.getNumNorma());
            jsonObject.put("descripcion", norma.getDescripcion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void deleteNorma(int idNorma, Callback callback) {
        String url = SUPABASE_URL + "/normasdet?id_norma=eq." + idNorma;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void getNormas(Callback callback) {
        String url = SUPABASE_URL + "/normasdet?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }


    // ===================== VEHICULO ===================== //



    public static void insertVehiculo(Vehiculo vehiculo, Callback callback) {
        String url = SUPABASE_URL + "/vehiculo";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("numplaca", vehiculo.getNumPlaca());
            jsonObject.put("marca", vehiculo.getMarca());
            jsonObject.put("modelo", vehiculo.getModelo());
            jsonObject.put("motor", vehiculo.getMotor());
            jsonObject.put("year", vehiculo.getYear());
            jsonObject.put("cedulap", vehiculo.getCedulaP()); // FK a propietario

            // Conversión del byte[] de la imagen a base64
            if (vehiculo.getMedia() != null) {
                String mediaBase64 = Base64.encodeToString(vehiculo.getMedia(), Base64.NO_WRAP);
                jsonObject.put("media", mediaBase64);
            } else {
                jsonObject.put("media", JSONObject.NULL);  // Si no hay imagen
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void updateVehiculo(String numPlaca, Vehiculo vehiculo, Callback callback) {
        String url = SUPABASE_URL + "/vehiculo?numplaca=eq." + numPlaca;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("marca", vehiculo.getMarca());
            jsonObject.put("modelo", vehiculo.getModelo());
            jsonObject.put("motor", vehiculo.getMotor());
            jsonObject.put("year", vehiculo.getYear());
            jsonObject.put("media", vehiculo.getMedia());
            jsonObject.put("cedulap", vehiculo.getCedulaP()); // FK
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void deleteVehiculo(String numPlaca, Callback callback) {
        String url = SUPABASE_URL + "/vehiculo?numplaca=eq." + numPlaca;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void getVehiculos(Callback callback) {
        String url = SUPABASE_URL + "/vehiculo?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }



/*
    public static void getVehiculos(Callback callback) {
        String url = SUPABASE_URL + "/vehiculo?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();

                    try {
                        JSONArray jsonArray = new JSONArray(responseBody);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject vehiculo = jsonArray.getJSONObject(i);

                            if (vehiculo.has("media") && !vehiculo.isNull("media")) {
                                String mediaBase64 = vehiculo.getString("media");
                                byte[] mediaBytes = Base64.decode(mediaBase64, Base64.DEFAULT);
                                vehiculo.put("media", mediaBytes);
                            }
                        }

                        callback.onResponse(call, response.newBuilder()
                                .body(ResponseBody.create(responseBody, MediaType.parse("application/json")))
                                .build());

                    } catch (Exception e) {
                        callback.onFailure(call, new IOException("Error parsing JSON", e));
                    }

                } else {
                    callback.onFailure(call, new IOException("Error en respuesta: " + response.code()));
                }
            }
        });
    }
*/





    public interface OnImageUploadListener {
        void onSuccess(String publicUrl);
        void onFailure(String errorMessage);
    }

    public static void uploadImageToSupabaseStorage(Context context, Uri imageUri, String fileName, OnImageUploadListener listener) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            byte[] imageBytes = byteBuffer.toByteArray();

            RequestBody fileBody = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));

            Request request = new Request.Builder()
                    .url(SUPABASE_STORAGE_URL + "/object/vehiculos/" + fileName)
                    .post(fileBody)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "image/jpeg")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onFailure("Fallo al subir imagen: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String publicUrl = SUPABASE_STORAGE_PUBLIC_URL + "/vehiculos/" + fileName;
                        listener.onSuccess(publicUrl);
                    } else {
                        listener.onFailure("Error al subir imagen: Código " + response.code());
                    }
                }
            });

        } catch (Exception e) {
            listener.onFailure("Excepción: " + e.getMessage());
        }
    }









    // ===================== OFICINAGOB ===================== //

    public static void insertOficinaGob(OficinaGob oficinaGob, Callback callback) {
        String url = SUPABASE_URL + "/oficinagob";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("valorvehiculo", oficinaGob.getValorVehiculo());
            jsonObject.put("npoliza", oficinaGob.getnPoliza());
            jsonObject.put("numplaca", oficinaGob.getNumPlaca());
            jsonObject.put("ubicacion", oficinaGob.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void updateOficinaGob(int idOficinaGob, OficinaGob oficinaGob, Callback callback) {
        String url = SUPABASE_URL + "/oficinagob?id_oficinagob=eq." + idOficinaGob;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("valorvehiculo", oficinaGob.getValorVehiculo());
            jsonObject.put("npoliza", oficinaGob.getnPoliza());
            jsonObject.put("numplaca", oficinaGob.getNumPlaca());
            jsonObject.put("ubicacion", oficinaGob.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void deleteOficinaGob(int idOficinaGob, Callback callback) {
        String url = SUPABASE_URL + "/oficinagob?id_oficinagob=eq." + idOficinaGob;

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=representation")
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void getOficinaGobs(Callback callback) {
        String url = SUPABASE_URL + "/oficinagob?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(callback);
    }



    // ===================== PUESDECONTROL ===================== //

    public static void insertarPuestoControl(PuesDeControl puesto, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_zona", puesto.getIdZona());
            json.put("ubicacion", puesto.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/puesdecontrol")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void listarPuestosControl(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/puesdecontrol?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void actualizarPuestoControl(int idPuestoControl, PuesDeControl puesto, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_zona", puesto.getIdZona());
            json.put("ubicacion", puesto.getUbicacion());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/puesdecontrol?id_puesdecontrol=eq." + idPuestoControl)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Prefer", "return=representation")
                .patch(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void eliminarPuestoControl(int idPuestoControl, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/puesdecontrol?id_puesdecontrol=eq." + idPuestoControl)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }


    // ===================== AGENTE===================== //

    public static void insertarAgente(Agente agente, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("cedulaa", agente.getCedulaA());
            json.put("nombre", agente.getNombre());
            json.put("id_puesdecontrol", agente.getIdPuestoControl());
            json.put("rango", agente.getRango());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/agente")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void listarAgentes(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/agente?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void actualizarAgente(int idAgente, Agente agente, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("cedulaa", agente.getCedulaA());
            json.put("nombre", agente.getNombre());
            json.put("id_puesdecontrol", agente.getIdPuestoControl());
            json.put("rango", agente.getRango());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/agente?id_agente=eq." + idAgente)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Prefer", "return=representation")
                .patch(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void eliminarAgente(int idAgente, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/agente?id_agente=eq." + idAgente)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }




// ===================== INFRACCION===================== //

    public static void insertarInfraccion(Infraccion infraccion, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_agente", infraccion.getIdAgente());
            json.put("numplaca", infraccion.getNumPlaca());
            json.put("valormulta", infraccion.getValorMulta());
            json.put("fecha", infraccion.getFecha());
            json.put("id_norma", infraccion.getIdNorma());
            json.put("hora", infraccion.getHora());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/infraccion")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void listarInfracciones(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/infraccion?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void actualizarInfraccion(int idInfraccion, Infraccion infraccion, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_agente", infraccion.getIdAgente());
            json.put("numplaca", infraccion.getNumPlaca());
            json.put("valormulta", infraccion.getValorMulta());
            json.put("fecha", infraccion.getFecha());
            json.put("id_norma", infraccion.getIdNorma());
            json.put("hora", infraccion.getHora());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/infraccion?id_infraccion=eq." + idInfraccion)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Prefer", "return=representation")
                .patch(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public static void eliminarInfraccion(int idInfraccion, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/infraccion?id_infraccion=eq." + idInfraccion)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }






    // INSERTAR accidente
    public static void insertarAccidente(Accidente accidente, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("numplaca", accidente.getNumPlaca());
            json.put("id_agente", accidente.getIdAgente());
            json.put("hora", accidente.getHora());
            json.put("fecha", accidente.getFecha());
            json.put("descripcion", accidente.getDescripcion());
            json.put("latitud", accidente.getLatitud());
            json.put("longitud", accidente.getLongitud());
            json.put("media", Base64.encodeToString(accidente.getMedia(), Base64.NO_WRAP)); // Convertir a base64
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/accidente")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // LISTAR accidentes
    public static void listarAccidentes(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/accidente?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    // ACTUALIZAR accidente
    public static void actualizarAccidente(int idAccidente, Accidente accidente, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("numplaca", accidente.getNumPlaca());
            json.put("id_agente", accidente.getIdAgente());
            json.put("hora", accidente.getHora());
            json.put("fecha", accidente.getFecha());
            json.put("descripcion", accidente.getDescripcion());
            json.put("latitud", accidente.getLatitud());
            json.put("longitud", accidente.getLongitud());
            json.put("media", Base64.encodeToString(accidente.getMedia(), Base64.NO_WRAP));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/accidente?id_accidente=eq." + idAccidente)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Prefer", "return=representation")
                .patch(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // ELIMINAR accidente
    public static void eliminarAccidente(int idAccidente, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/accidente?id_accidente=eq." + idAccidente)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }






    // INSERTAR acta
    public static void insertarActa(Acta acta, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_accidente", acta.getIdAccidente());
            json.put("id_audiencia", acta.getIdAudiencia());
            json.put("hora", acta.getHora());
            json.put("id_zona", acta.getIdZona());
            json.put("id_agente", acta.getIdAgente());
            json.put("fecha", acta.getFecha());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/acta")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // LISTAR actas
    public static void listarActas(Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/acta?select=*")
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    // ACTUALIZAR acta
    public static void actualizarActa(int idActa, Acta acta, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("id_accidente", acta.getIdAccidente());
            json.put("id_audiencia", acta.getIdAudiencia());
            json.put("hora", acta.getHora());
            json.put("id_zona", acta.getIdZona());
            json.put("id_agente", acta.getIdAgente());
            json.put("fecha", acta.getFecha());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/acta?id_acta=eq." + idActa)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Prefer", "return=representation")
                .patch(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    // ELIMINAR acta
    public static void eliminarActa(int idActa, Callback callback) {
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/acta?id_acta=eq." + idActa)
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }










}
