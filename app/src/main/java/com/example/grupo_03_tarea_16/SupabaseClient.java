package com.example.grupo_03_tarea_16;

import com.example.grupo_03_tarea_16.modelo.Audiencia;
import com.example.grupo_03_tarea_16.modelo.NormasDeT;
import com.example.grupo_03_tarea_16.modelo.OficinaGob;
import com.example.grupo_03_tarea_16.modelo.Propietario;
import com.example.grupo_03_tarea_16.modelo.PuesDeControl;
import com.example.grupo_03_tarea_16.modelo.Vehiculo;
import com.example.grupo_03_tarea_16.modelo.Zona;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SupabaseClient {

    private static final String SUPABASE_URL = "https://wqpmwrgkkgfzdpsiyxhq.supabase.co/rest/v1";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndxcG13cmdra2dmemRwc2l5eGhxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMwMjM5OTYsImV4cCI6MjA2ODU5OTk5Nn0.WAXHiheRYrutcun7hcXlAJ8JGk2wacvqZnyjkfeqPKo";

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









}
