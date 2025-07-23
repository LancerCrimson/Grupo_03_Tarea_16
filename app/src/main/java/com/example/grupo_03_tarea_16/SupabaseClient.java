package com.example.grupo_03_tarea_16;

import com.example.grupo_03_tarea_16.modelo.Propietario;

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







}
